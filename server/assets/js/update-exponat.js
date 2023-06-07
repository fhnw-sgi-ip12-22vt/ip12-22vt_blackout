const selectRoomId = document.querySelector('select[name="selectRoomId"]');
const exponatTitle = document.querySelector('input[name="exponatTitle"]');
const exponatDescription = document.querySelector('input[name="exponatDescription"]');

function getQueryParameterValue(key) {
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    return urlParams.get(key);
}

const form = document.querySelector('#form');
const hiddenInput = document.createElement('input');
hiddenInput.type = 'hidden';
hiddenInput.name = 'exponatId';
hiddenInput.value = getQueryParameterValue('exponatId');
form.appendChild(hiddenInput);

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
    }, 3000);
  })

function fillSelectWithOptions(options) {
  options.forEach(option => {
    const optionElement = document.createElement('option');
    optionElement.value = option.value;
    optionElement.text = option.label;
    selectRoomId.appendChild(optionElement);
  });
  selectRoomId.value = getQueryParameterValue('roomId');
}

function fillFormFields() {
    exponatTitle.value = getQueryParameterValue('exponatTitle');
    exponatDescription.value = getQueryParameterValue('exponatDescription');
}

fillFormFields();