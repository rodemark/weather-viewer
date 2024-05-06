function hideNotification(elementId, timeout) {
    const notificationElement = document.getElementById(elementId);

    if (notificationElement) {
        setTimeout(function() {
            notificationElement.style.display = 'none';
        }, timeout);
    }
}