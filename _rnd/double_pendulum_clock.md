---
title: "Double Pendulum Clock"
date: 2026-09-09 
permalink: /rnd/double-pendulum-clock
last_modified_at: 2026-09-09
header:
  og_image: /assets/images/rnd/og-clock-header.jpg
---

<style>
    /* Scoped styles for the canvas container & button */
    #clock-wrapper {
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        margin: 20px 0;
        padding: 20px;
    }
    #canvas-container {
        position: relative;
        box-shadow: 0 35px 70px rgba(0, 0, 0, 0.95), 0 0 40px rgba(160, 110, 20, 0.1);
        border-radius: 50%;
        background: #080503;
        max-width: 100%;
    }
    #clockCanvas {
        display: block;
        border-radius: 50%;
        max-width: 100%;
        height: auto;
    }
    .clock-ui-panel {
        margin-top: 20px;
        z-index: 10;
    }
    .clock-btn {
        background: linear-gradient(135deg, #24160b 0%, #120a04 100%);
        color: #d9b875;
        border: 1px solid #785a25;
        padding: 10px 24px;
        border-radius: 25px;
        cursor: pointer;
        font-family: 'Georgia', serif;
        font-size: 13px;
        letter-spacing: 1px;
        box-shadow: 0 4px 12px rgba(0,0,0,0.7);
        transition: all 0.25s ease;
    }
    .clock-btn:hover {
        background: linear-gradient(135deg, #362110 0%, #1f1106 100%);
        border-color: #c29b2c;
        color: #ffffff;
        box-shadow: 0 0 14px rgba(194, 155, 44, 0.35);
    }
</style>

<div id="clock-wrapper">
    <div id="canvas-container">
        <canvas id="clockCanvas" width="760" height="760"></canvas>
    </div>
    <div class="clock-ui-panel">
        <button id="resetBtn" class="clock-btn">Restart Near Top</button>
    </div>
</div>

<script>
    (function() {
        const canvas = document.getElementById('clockCanvas');
        const ctx = canvas.getContext('2d');
        const resetBtn = document.getElementById('resetBtn');

        const width = canvas.width;
        const height = canvas.height;
        const cx = width / 2;
        const cy = height / 2;
        const radius = width * 0.43;

        const L1_full = radius * 0.65;
        const L1 = radius * 0.36;
        const L2 = radius * 0.52;
        const m1 = 1.5;
        const m2 = 0.9;
        const g = 980;

        let state = [0, 0, 0, 0];
        let prevT1 = Math.PI;
        let accumulatedT1 = 0;

        function initPendulum() {
            const delta1 = (Math.random() - 0.5) * 0.12;
            const delta2 = (Math.random() - 0.5) * 0.12;
            state = [Math.PI + delta1, Math.PI + delta2, 0, 0];
            prevT1 = state[0];
            accumulatedT1 = 0;
        }

        function getDerivatives(s) {
            const [t1, t2, w1, w2] = s;
            const delta = t1 - t2;

            const den1 = L1 * (2 * m1 + m2 - m2 * Math.cos(2 * t1 - 2 * t2));
            const num1 = -g * (2 * m1 + m2) * Math.sin(t1) 
                         - m2 * g * Math.sin(t1 - 2 * t2) 
                         - 2 * Math.sin(delta) * m2 * (w2 * w2 * L2 + w1 * w1 * L1 * Math.cos(delta));
            const alpha1 = num1 / den1;

            const den2 = L2 * (2 * m1 + m2 - m2 * Math.cos(2 * t1 - 2 * t2));
            const num2 = 2 * Math.sin(delta) * (
                w1 * w1 * L1 * (m1 + m2) 
                + g * (m1 + m2) * Math.cos(t1) 
                + w2 * w2 * L2 * m2 * Math.cos(delta)
            );
            const alpha2 = num2 / den2;

            return [w1, w2, alpha1, alpha2];
        }

        function rk4Step(s, dt) {
            const k1 = getDerivatives(s);
            const s2 = [s[0] + 0.5 * dt * k1[0], s[1] + 0.5 * dt * k1[1], s[2] + 0.5 * dt * k1[2], s[3] + 0.5 * dt * k1[3]];
            const k2 = getDerivatives(s2);
            const s3 = [s[0] + 0.5 * dt * k2[0], s[1] + 0.5 * dt * k2[1], s[2] + 0.5 * dt * k2[2], s[3] + 0.5 * dt * k2[3]];
            const k3 = getDerivatives(s3);
            const s4 = [s[0] + dt * k3[0], s[1] + dt * k3[1], s[2] + dt * k3[2], s[3] + dt * k3[3]];
            const k4 = getDerivatives(s4);

            return [
                s[0] + (dt / 6) * (k1[0] + 2 * k2[0] + 2 * k3[0] + k4[0]),
                s[1] + (dt / 6) * (k1[1] + 2 * k2[1] + 2 * k3[1] + k4[1]),
                s[2] + (dt / 6) * (k1[2] + 2 * k2[2] + 2 * k3[2] + k4[2]),
                s[3] + (dt / 6) * (k1[3] + 2 * k2[3] + 2 * k3[3] + k4[3])
            ];
        }

        const romanNumerals = ["XII", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "XI"];

        function drawAntiqueClockFace() {
            const bezelGrad = ctx.createRadialGradient(cx, cy, radius * 0.95, cx, cy, radius * 1.12);
            bezelGrad.addColorStop(0, '#1f130a');
            bezelGrad.addColorStop(0.5, '#331f10');
            bezelGrad.addColorStop(1, '#0a0603');

            ctx.beginPath();
            ctx.arc(cx, cy, radius * 1.12, 0, Math.PI * 2);
            ctx.fillStyle = bezelGrad;
            ctx.fill();

            const brassGrad = ctx.createRadialGradient(cx, cy, radius * 0.98, cx, cy, radius * 1.03);
            brassGrad.addColorStop(0, '#6e501c');
            brassGrad.addColorStop(0.35, '#c29b2c');
            brassGrad.addColorStop(0.7, '#f5eb9d');
            brassGrad.addColorStop(1, '#4d370f');

            ctx.beginPath();
            ctx.arc(cx, cy, radius * 1.03, 0, Math.PI * 2);
            ctx.fillStyle = brassGrad;
            ctx.fill();

            const parchmentGrad = ctx.createRadialGradient(cx, cy, 10, cx, cy, radius);
            parchmentGrad.addColorStop(0, '#faf6eb');
            parchmentGrad.addColorStop(0.75, '#ebdcb9');
            parchmentGrad.addColorStop(1, '#c9b082');

            ctx.beginPath();
            ctx.arc(cx, cy, radius, 0, Math.PI * 2);
            ctx.fillStyle = parchmentGrad;
            ctx.fill();

            const innerShadow = ctx.createRadialGradient(cx, cy, radius * 0.85, cx, cy, radius);
            innerShadow.addColorStop(0, 'rgba(0,0,0,0)');
            innerShadow.addColorStop(1, 'rgba(35, 20, 5, 0.35)');
            ctx.beginPath();
            ctx.arc(cx, cy, radius, 0, Math.PI * 2);
            ctx.fillStyle = innerShadow;
            ctx.fill();

            ctx.lineWidth = 2;
            ctx.strokeStyle = '#33200e';
            ctx.beginPath();
            ctx.arc(cx, cy, radius * 0.96, 0, Math.PI * 2);
            ctx.stroke();

            ctx.beginPath();
            ctx.arc(cx, cy, radius * 0.83, 0, Math.PI * 2);
            ctx.stroke();

            ctx.save();
            ctx.translate(cx, cy);

            for (let i = 0; i < 60; i++) {
                const angle = (i * Math.PI) / 30;
                const isHour = i % 5 === 0;

                ctx.beginPath();
                ctx.rotate(angle);

                if (isHour) {
                    ctx.moveTo(0, -radius * 0.96);
                    ctx.lineTo(0, -radius * 0.83);
                    ctx.lineWidth = 2.5;
                    ctx.strokeStyle = '#211206';
                } else {
                    ctx.moveTo(0, -radius * 0.96);
                    ctx.lineTo(0, -radius * 0.92);
                    ctx.lineWidth = 1;
                    ctx.strokeStyle = '#4d3318';
                }
                ctx.stroke();
                ctx.rotate(-angle);
            }

            ctx.fillStyle = '#1c0f05';
            ctx.font = 'bold 24px "Georgia", "Times New Roman", serif';
            ctx.textAlign = 'center';
            ctx.textBaseline = 'middle';

            for (let i = 0; i < 12; i++) {
                const angle = (i * Math.PI) / 6;
                const rNum = radius * 0.73;
                const x = rNum * Math.sin(angle);
                const y = -rNum * Math.cos(angle);
                ctx.fillText(romanNumerals[i], x, y + 2);
            }

            ctx.save();
            ctx.fillStyle = '#2b1a0c';
            ctx.font = 'italic 17px "Georgia", "Times New Roman", serif';
            ctx.textAlign = 'center';
            ctx.textBaseline = 'middle';
            
            const sigY = radius * 0.42; 
            ctx.fillText("G. Frison", 0, sigY);

            ctx.beginPath();
            ctx.moveTo(-22, sigY + 12);
            ctx.quadraticCurveTo(0, sigY + 16, 22, sigY + 12);
            ctx.strokeStyle = '#6e5124';
            ctx.lineWidth = 0.8;
            ctx.stroke();

            ctx.restore();
            ctx.restore();
        }

        function drawSunMoonWindow(hour24) {
            const wx = cx;
            const wy = cy - radius * 0.41;
            const wWidth = radius * 0.42;
            const wHeight = radius * 0.24;

            const rTop = wWidth / 2;
            const aperturePath = new Path2D();
            aperturePath.arc(wx, wy - wHeight * 0.1, rTop, Math.PI, 0, false);
            aperturePath.arc(wx + wWidth * 0.25, wy + wHeight * 0.2, wWidth * 0.25, -Math.PI / 2, Math.PI / 2, true);
            aperturePath.lineTo(wx - wWidth * 0.25, wy + wHeight * 0.45);
            aperturePath.arc(wx - wWidth * 0.25, wy + wHeight * 0.2, wWidth * 0.25, Math.PI / 2, -Math.PI / 2, true);
            aperturePath.closePath();

            ctx.save();
            ctx.clip(aperturePath);

            const diskRadius = wWidth * 0.95;
            const diskCx = wx;
            const diskCy = wy + wWidth * 0.28;
            const celestialR = wWidth * 0.62;

            const diskAngle = ((hour24 - 12) / 24) * 2 * Math.PI;

            ctx.save();
            ctx.translate(diskCx, diskCy);
            ctx.rotate(diskAngle);

            const dayGrad = ctx.createRadialGradient(0, -celestialR, 5, 0, -celestialR, diskRadius * 1.1);
            dayGrad.addColorStop(0, '#4f7294');
            dayGrad.addColorStop(0.6, '#213d52');
            dayGrad.addColorStop(1, '#0e1a26');

            ctx.beginPath();
            ctx.arc(0, 0, diskRadius * 1.3, 0, Math.PI, true);
            ctx.fillStyle = dayGrad;
            ctx.fill();

            const sunY = -celestialR;
            ctx.save();
            ctx.translate(0, sunY);

            ctx.strokeStyle = '#c29b2c';
            ctx.lineWidth = 1.5;
            for (let a = 0; a < 12; a++) {
                const rayAngle = (a * Math.PI) / 6;
                ctx.beginPath();
                ctx.moveTo(13 * Math.cos(rayAngle), 13 * Math.sin(rayAngle));
                ctx.lineTo(19 * Math.cos(rayAngle), 19 * Math.sin(rayAngle));
                ctx.stroke();
            }

            const sunGrad = ctx.createRadialGradient(-2, -2, 2, 0, 0, 11);
            sunGrad.addColorStop(0, '#fff3a8');
            sunGrad.addColorStop(0.5, '#e6b800');
            sunGrad.addColorStop(1, '#997a00');

            ctx.beginPath();
            ctx.arc(0, 0, 11, 0, Math.PI * 2);
            ctx.fillStyle = sunGrad;
            ctx.fill();
            ctx.strokeStyle = '#524100';
            ctx.lineWidth = 1;
            ctx.stroke();
            ctx.restore();

            const nightGrad = ctx.createRadialGradient(0, celestialR, 5, 0, celestialR, diskRadius * 1.1);
            nightGrad.addColorStop(0, '#0d1826');
            nightGrad.addColorStop(0.6, '#050c18');
            nightGrad.addColorStop(1, '#010308');

            ctx.beginPath();
            ctx.arc(0, 0, diskRadius * 1.3, 0, Math.PI, false);
            ctx.fillStyle = nightGrad;
            ctx.fill();

            ctx.fillStyle = '#e3d29f';
            const starCoords = [
                [-25, celestialR - 10], [-14, celestialR + 8], [-32, celestialR],
                [20, celestialR - 8], [14, celestialR + 10], [30, celestialR - 2], [0, celestialR - 18]
            ];
            starCoords.forEach(([sx, sy]) => {
                ctx.beginPath();
                ctx.arc(sx, sy, 1.3, 0, Math.PI * 2);
                ctx.fill();
            });

            const moonY = celestialR;
            ctx.save();
            ctx.translate(0, moonY);

            const moonGrad = ctx.createRadialGradient(-3, -3, 2, 0, 0, 11);
            moonGrad.addColorStop(0, '#f2ede4');
            moonGrad.addColorStop(0.6, '#d1c9b8');
            moonGrad.addColorStop(1, '#948d7c');

            ctx.beginPath();
            ctx.arc(0, 0, 11, 0, Math.PI * 2);
            ctx.fillStyle = moonGrad;
            ctx.fill();
            ctx.strokeStyle = '#3b372e';
            ctx.lineWidth = 0.8;
            ctx.stroke();

            ctx.fillStyle = 'rgba(90, 80, 65, 0.35)';
            ctx.beginPath();
            ctx.arc(-3, -2, 2.5, 0, Math.PI * 2);
            ctx.arc(3, 3, 2, 0, Math.PI * 2);
            ctx.arc(2, -4, 1.5, 0, Math.PI * 2);
            ctx.fill();

            const moonPhaseAngle = (accumulatedT1 / (2 * Math.PI * 29.53)) * 2 * Math.PI;
            const shadowShift = Math.sin(moonPhaseAngle) * 11;
            ctx.fillStyle = 'rgba(3, 8, 16, 0.8)';
            ctx.beginPath();
            ctx.arc(shadowShift * 0.5, 0, 11, 0, Math.PI * 2);
            ctx.fill();

            ctx.restore();
            ctx.restore();
            ctx.restore();

            ctx.save();
            ctx.lineWidth = 3.5;
            ctx.strokeStyle = '#c29b2c';
            ctx.shadowColor = 'rgba(0,0,0,0.8)';
            ctx.shadowBlur = 5;
            ctx.stroke(aperturePath);

            ctx.lineWidth = 1;
            ctx.strokeStyle = '#42300b';
            ctx.shadowBlur = 0;
            ctx.stroke(aperturePath);

            ctx.restore();
        }

        function drawHourHand(x0, y0, angle, length) {
            ctx.save();
            ctx.translate(x0, y0);
            ctx.rotate(angle);

            ctx.shadowColor = 'rgba(0, 0, 0, 0.4)';
            ctx.shadowBlur = 6;
            ctx.shadowOffsetX = 3;
            ctx.shadowOffsetY = 3;

            ctx.beginPath();
            ctx.arc(0, -18, 6, -Math.PI / 2, Math.PI * 1.5);
            ctx.moveTo(0, 0);

            const w1 = 7;
            const w2 = 4;
            const spadeRadius = 14;
            const spadeCenter = length - 28;

            ctx.moveTo(-w1 / 2, 0);
            ctx.bezierCurveTo(-w1, length * 0.25, -w2 * 1.5, length * 0.45, -w2 / 2, spadeCenter - spadeRadius);
            
            ctx.arc(0, spadeCenter, spadeRadius, Math.PI * 0.75, -Math.PI * 0.25, false);
            ctx.lineTo(0, length);
            ctx.lineTo(spadeRadius * Math.sin(Math.PI * 0.25), spadeCenter + spadeRadius * Math.cos(Math.PI * 0.25));
            ctx.arc(0, spadeCenter, spadeRadius, Math.PI * 0.25, Math.PI * 0.75, false);

            ctx.bezierCurveTo(w2 * 1.5, length * 0.45, w1, length * 0.25, w1 / 2, 0);
            ctx.closePath();

            ctx.fillStyle = '#140c06';
            ctx.fill();
            ctx.lineWidth = 1;
            ctx.strokeStyle = '#4a3316';
            ctx.stroke();

            ctx.beginPath();
            ctx.arc(0, spadeCenter, spadeRadius * 0.48, 0, Math.PI * 2);
            ctx.fillStyle = '#ebdcb9';
            ctx.fill();
            ctx.strokeStyle = '#140c06';
            ctx.lineWidth = 1.2;
            ctx.stroke();

            ctx.beginPath();
            ctx.moveTo(0, 8);
            ctx.lineTo(0, spadeCenter - spadeRadius - 2);
            ctx.strokeStyle = '#8c6b2d';
            ctx.lineWidth = 1.2;
            ctx.stroke();

            ctx.restore();
        }

        function drawMinuteHand(x0, y0, angle, length) {
            ctx.save();
            ctx.translate(x0, y0);
            ctx.rotate(angle);

            ctx.shadowColor = 'rgba(0, 0, 0, 0.45)';
            ctx.shadowBlur = 6;
            ctx.shadowOffsetX = 3;
            ctx.shadowOffsetY = 3;

            ctx.beginPath();
            ctx.arc(0, -14, 4.5, 0, Math.PI * 2);
            ctx.fillStyle = '#170d06';
            ctx.fill();

            const wStart = 5;
            const wEnd = 2;
            const tipCenter = length - 22;
            const tipRadius = 10;

            ctx.beginPath();
            ctx.moveTo(-wStart / 2, 0);
            ctx.lineTo(-wEnd / 2, tipCenter - tipRadius);

            ctx.lineTo(-tipRadius, tipCenter);
            ctx.lineTo(-tipRadius * 0.4, tipCenter + 2);
            ctx.lineTo(0, length);
            ctx.lineTo(tipRadius * 0.4, tipCenter + 2);
            ctx.lineTo(tipRadius, tipCenter);
            ctx.lineTo(wEnd / 2, tipCenter - tipRadius);

            ctx.lineTo(wStart / 2, 0);
            ctx.closePath();

            ctx.fillStyle = '#180e07';
            ctx.fill();
            ctx.lineWidth = 1;
            ctx.strokeStyle = '#543b19';
            ctx.stroke();

            ctx.beginPath();
            ctx.moveTo(0, tipCenter - tipRadius * 0.6);
            ctx.lineTo(-tipRadius * 0.45, tipCenter);
            ctx.lineTo(0, tipCenter + tipRadius * 0.6);
            ctx.lineTo(tipRadius * 0.45, tipCenter);
            ctx.closePath();
            ctx.fillStyle = '#ebdcb9';
            ctx.fill();
            ctx.strokeStyle = '#180e07';
            ctx.lineWidth = 1;
            ctx.stroke();

            ctx.beginPath();
            ctx.moveTo(0, 5);
            ctx.lineTo(0, tipCenter - tipRadius - 2);
            ctx.strokeStyle = '#9c7731';
            ctx.lineWidth = 1;
            ctx.stroke();

            ctx.restore();
        }

        let lastTime = performance.now();

        function animate(now) {
            let dt = (now - lastTime) / 1000;
            lastTime = now;

            if (dt > 0.05) dt = 0.05;

            const subSteps = 16;
            const subDt = dt / subSteps;
            for (let i = 0; i < subSteps; i++) {
                state = rk4Step(state, subDt);

                let deltaT1 = state[0] - prevT1;
                prevT1 = state[0];

                while (deltaT1 > Math.PI) deltaT1 -= 2 * Math.PI;
                while (deltaT1 < -Math.PI) deltaT1 += 2 * Math.PI;

                accumulatedT1 -= deltaT1;
            }

            const [t1, t2] = state;
            const totalHours = 12.0 + (accumulatedT1 / (2 * Math.PI)) * 12.0;
            const hour24 = ((totalHours % 24) + 24) % 24;

            const x0 = cx;
            const y0 = cy;

            const dir1 = { x: Math.sin(t1), y: Math.cos(t1) };
            const x_anchor = x0 + L1 * dir1.x;
            const y_anchor = y0 + L1 * dir1.y;

            const angle1 = Math.atan2(-dir1.x, dir1.y);
            const dir2 = { x: Math.sin(t2), y: Math.cos(t2) };
            const angle2 = Math.atan2(-dir2.x, dir2.y);

            ctx.clearRect(0, 0, width, height);
            drawAntiqueClockFace();
            drawSunMoonWindow(hour24);

            drawHourHand(x0, y0, angle1, L1_full);
            drawMinuteHand(x_anchor, y_anchor, angle2, L2);

            const capGrad1 = ctx.createRadialGradient(x0 - 2, y0 - 2, 1, x0, y0, 12);
            capGrad1.addColorStop(0, '#f5eb9d');
            capGrad1.addColorStop(0.4, '#c29b2c');
            capGrad1.addColorStop(1, '#2e1f06');

            ctx.beginPath();
            ctx.arc(x0, y0, 12, 0, Math.PI * 2);
            ctx.fillStyle = capGrad1;
            ctx.fill();
            ctx.lineWidth = 1.2;
            ctx.strokeStyle = '#170c04';
            ctx.stroke();

            ctx.beginPath();
            ctx.arc(x0, y0, 3.5, 0, Math.PI * 2);
            ctx.fillStyle = '#170c04';
            ctx.fill();

            const capGrad2 = ctx.createRadialGradient(x_anchor - 1, y_anchor - 1, 1, x_anchor, y_anchor, 8);
            capGrad2.addColorStop(0, '#f5eb9d');
            capGrad2.addColorStop(0.5, '#b58f24');
            capGrad2.addColorStop(1, '#261804');

            ctx.beginPath();
            ctx.arc(x_anchor, y_anchor, 8, 0, Math.PI * 2);
            ctx.fillStyle = capGrad2;
            ctx.fill();
            ctx.lineWidth = 1;
            ctx.strokeStyle = '#170c04';
            ctx.stroke();

            ctx.beginPath();
            ctx.arc(x_anchor, y_anchor, 2.5, 0, Math.PI * 2);
            ctx.fillStyle = '#170c04';
            ctx.fill();

            requestAnimationFrame(animate);
        }

        resetBtn.addEventListener('click', () => {
            initPendulum();
        });

        initPendulum();
        requestAnimationFrame(animate);
    })();
</script>