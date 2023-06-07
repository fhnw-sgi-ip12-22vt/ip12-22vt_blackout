const selectExponatId = document.querySelector('select[name="selectExponatId"]');

fetch('exponats.json')
    .then((response) => response.json())
    .then((data) => {
        if (!data.exponats) return;
        fillSelectWithOptions(data.exponats);
    })
    .catch((error) => {
        console.log(error);
        setTimeout(() => {
            window.location.href = '/error.html';
        }, 3000)
    })

function fillSelectWithOptions(options) {
    options.forEach(option => {
        const optionElement = document.createElement('option');
        optionElement.value = option.value;
        optionElement.text = option.label;
        selectExponatId.appendChild(optionElement);
    });
}