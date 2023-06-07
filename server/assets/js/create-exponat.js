const selectRoomId = document.querySelector('select[name="selectRoomId"]');

fetch('rooms.json')
    .then((response) => response.json())
    .then((data) => {
        if (!data.rooms) return;
        fillSelectWithOptions(data.rooms);
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
        selectRoomId.appendChild(optionElement);
    });
}