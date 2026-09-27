/* Foodies - Professional Toast Notifications
   Replaces blocking browser alert popups with non-blocking messages. */
(function () {
    const style = document.createElement("style");
    style.textContent = `
        #foodies-toast-container {
            position: fixed;
            top: 22px;
            right: 22px;
            z-index: 99999;
            display: flex;
            flex-direction: column;
            gap: 12px;
            width: min(380px, calc(100vw - 32px));
            pointer-events: none;
        }

        .foodies-toast {
            background: #ffffff;
            color: #222222;
            border-left: 5px solid #ff4d2d;
            border-radius: 12px;
            padding: 14px 16px;
            box-shadow: 0 8px 28px rgba(0, 0, 0, 0.16);
            font: 600 14px/1.45 Arial, sans-serif;
            opacity: 0;
            transform: translateX(25px);
            transition: opacity .2s ease, transform .2s ease;
            pointer-events: auto;
            word-break: break-word;
        }

        .foodies-toast.show {
            opacity: 1;
            transform: translateX(0);
        }

        .foodies-toast.success { border-left-color: #16a34a; }
        .foodies-toast.error { border-left-color: #dc2626; }
        .foodies-toast.warning { border-left-color: #f59e0b; }
        .foodies-toast.info { border-left-color: #ff4d2d; }

        @media (max-width: 600px) {
            #foodies-toast-container {
                top: 14px;
                right: 14px;
            }
        }
    `;
    document.head.appendChild(style);

    function getContainer() {
        let container = document.getElementById("foodies-toast-container");

        if (!container) {
            container = document.createElement("div");
            container.id = "foodies-toast-container";
            document.body.appendChild(container);
        }

        return container;
    }

    window.showToast = function (message, type = "info", duration = 2800) {
        const container = getContainer();
        const toast = document.createElement("div");
        toast.className = `foodies-toast ${type}`;
        toast.textContent = String(message ?? "");

        container.appendChild(toast);

        requestAnimationFrame(() => {
            toast.classList.add("show");
        });

        setTimeout(() => {
            toast.classList.remove("show");
            setTimeout(() => toast.remove(), 220);
        }, duration);
    };

    // Keep existing project logic working while removing blocking browser popups.
    window.alert = function (message) {
        const text = String(message ?? "");
        const lower = text.toLowerCase();

        let type = "info";

        if (
            lower.includes("success") ||
            lower.includes("successfully") ||
            lower.includes("assigned") ||
            lower.includes("added") ||
            lower.includes("updated") ||
            lower.includes("deleted") ||
            lower.includes("created") ||
            lower.includes("payment successful")
        ) {
            type = "success";
        } else if (
            lower.includes("unable") ||
            lower.includes("failed") ||
            lower.includes("error") ||
            lower.includes("invalid") ||
            lower.includes("not found") ||
            lower.includes("required") ||
            lower.includes("please enter") ||
            lower.includes("missing")
        ) {
            type = "error";
        } else if (
            lower.includes("empty") ||
            lower.includes("select") ||
            lower.includes("coming next")
        ) {
            type = "warning";
        }

        window.showToast(text, type);
    };
})();
