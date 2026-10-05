---
layout: single
---

<!-- 1. CSS for PDF.js annotation/link overlay -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/pdfjs-dist@^4/web/pdf_viewer.css" />

{{ content }}

{% if page.pdf %}
  <div style="display: flex; justify-content: flex-end; align-items: center; gap: 10px; margin-bottom: 15px;">
    <button id="pdf-fullscreen" type="button" class="btn btn--primary" title="Full screen" aria-label="Full screen">
      <i class="fas fa-expand" aria-hidden="true"></i>
    </button>
    <a href="{{ page.pdf | relative_url }}" class="btn btn--primary" title="Download PDF" aria-label="Download PDF" download>
      <i class="fas fa-download" aria-hidden="true"></i>
    </a>
  </div>

  <div id="pdf-container" style="text-align: center; margin-top: 20px; overflow: auto; background: #f5f5f5;"></div>

  <!-- 2. Core PDF.js + Annotation Viewer JS (ES modules, pinned to major v4) -->
  <script type="module">
    import * as pdfjsLib from 'https://cdn.jsdelivr.net/npm/pdfjs-dist@^4/build/pdf.min.mjs';
    import * as pdfjsViewer from 'https://cdn.jsdelivr.net/npm/pdfjs-dist@^4/web/pdf_viewer.mjs';

    (async function() {
      const pdfUrl = "{{ page.pdf | relative_url }}";

      // Set worker source via jsDelivr
      pdfjsLib.GlobalWorkerOptions.workerSrc = 'https://cdn.jsdelivr.net/npm/pdfjs-dist@^4/build/pdf.worker.min.mjs';

      try {
        const pdf = await pdfjsLib.getDocument({ url: pdfUrl }).promise;
        const container = document.getElementById('pdf-container');
        const linkService = new pdfjsViewer.PDFLinkService({
          externalLinkTarget: pdfjsViewer.LinkTarget.BLANK,
          externalLinkRel: 'noopener noreferrer'
        });

        for (let pageNum = 1; pageNum <= pdf.numPages; pageNum++) {
          const page = await pdf.getPage(pageNum);
          const scale = 1.5;
          const viewport = page.getViewport({ scale: scale });

          const pageWrapper = document.createElement('div');
          pageWrapper.style.position = 'relative';
          pageWrapper.style.display = 'inline-block';
          pageWrapper.style.marginBottom = '20px';
          pageWrapper.style.boxShadow = '0 2px 8px rgba(0,0,0,0.15)';

          const canvas = document.createElement('canvas');
          const context = canvas.getContext('2d');
          canvas.height = viewport.height;
          canvas.width = viewport.width;
          canvas.style.display = 'block';
          canvas.style.maxWidth = '100%';
          canvas.style.height = 'auto';

          pageWrapper.appendChild(canvas);

          const annotationLayerDiv = document.createElement('div');
          annotationLayerDiv.className = 'annotationLayer';
          annotationLayerDiv.style.position = 'absolute';
          annotationLayerDiv.style.top = '0';
          annotationLayerDiv.style.left = '0';
          annotationLayerDiv.style.right = '0';
          annotationLayerDiv.style.bottom = '0';

          pageWrapper.appendChild(annotationLayerDiv);
          container.appendChild(pageWrapper);

          await page.render({ canvasContext: context, viewport: viewport }).promise;

          const annotations = await page.getAnnotations();
          const annotationLayer = new pdfjsLib.AnnotationLayer({
            div: annotationLayerDiv,
            accessibilityManager: null,
            annotationCanvasMap: null,
            page: page,
            viewport: viewport.clone({ dontFlip: true })
          });
          await annotationLayer.render({
            annotations: annotations,
            linkService: linkService,
            renderForms: false
          });
        }
      } catch (err) {
        console.error(err);
        document.getElementById('pdf-container').innerHTML = 
          '<p>Unable to load PDF. <a href="' + pdfUrl + '">Download document instead.</a></p>';
      }
    })();

    // Fullscreen toggle for the PDF viewer
    (function() {
      const btn = document.getElementById('pdf-fullscreen');
      const container = document.getElementById('pdf-container');
      if (!btn || !container) return;
      btn.addEventListener('click', function() {
        if (document.fullscreenElement) {
          document.exitFullscreen();
        } else if (container.requestFullscreen) {
          container.requestFullscreen();
        } else if (container.webkitRequestFullscreen) {
          container.webkitRequestFullscreen();
        }
      });
    })();
  </script>
{% endif %}