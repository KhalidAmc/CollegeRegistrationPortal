
document.addEventListener('submit', (event) => {
    const button = event.submitter;
    if (button && button.classList.contains('btn-danger')) {
        const confirmed = window.confirm('Are you sure you want to continue?');
        if (!confirmed) {
            event.preventDefault();
        }
    }
});
