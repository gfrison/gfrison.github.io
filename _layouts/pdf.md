---
layout: single
---
<article class="post">

  <div class="post-content">
    {{ content }}
  </div>

  {% if page.pdf %}
    <div style="text-align: right; margin-bottom: 15px;">
     <a href="{{ page.pdf | relative_url }}" class="btn btn--primary" download>
      Download PDF
     </a>
    </div> 
    <div id="pdf-container" style="text-align: center; margin-top: 20px;"></div>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/pdf.js/3.11.174/pdf.min.js"></script>
    <script>
      const pdfUrl = "{{ page.pdf | relative_url }}";
      pdfjsLib.GlobalWorkerOptions.workerSrc = 'https://cdnjs.cloudflare.com/ajax/libs/pdf.js/3.11.174/pdf.worker.min.js';

      pdfjsLib.getDocument(pdfUrl).promise.then(pdf => {
        const container = document.getElementById('pdf-container');
        for (let pageNum = 1; pageNum <= pdf.numPages; pageNum++) {
          pdf.getPage(pageNum).then(page => {
            const scale = 1.5;
            const viewport = page.getViewport({ scale: scale });

            const canvas = document.createElement('canvas');
            const context = canvas.getContext('2d');
            canvas.height = viewport.height;
            canvas.width = viewport.width;
            canvas.style.maxWidth = '100%';
            canvas.style.height = 'auto';
            canvas.style.marginBottom = '15px';
            canvas.style.boxShadow = '0 2px 8px rgba(0,0,0,0.15)';

            container.appendChild(canvas);
            page.render({ canvasContext: context, viewport: viewport });
          });
        }
      }).catch(err => {
        document.getElementById('pdf-container').innerHTML = 
          '<p>Unable to load PDF. <a href="' + pdfUrl + '">Download document instead.</a></p>';
      });
    </script>
  {% endif %}
</article>