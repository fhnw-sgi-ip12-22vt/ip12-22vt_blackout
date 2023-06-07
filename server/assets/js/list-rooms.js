fetch('rooms.json')
    .then(response => response.json())
    .then(data => {
        if (!data.rooms) return;
        let rooms = data.rooms;

        if (rooms.length > 0) {
            document.getElementById('no-content').innerHTML = '';
        }

        rooms.forEach(room => {
            let element = document.createElement('div');
            element.classList.add('row', 'gy-4', 'row-cols-1', 'row-cols-md-2', 'row-cols-xl-3');
            element.style.marginBottom = '10px';

            let col1 = document.createElement('div');
            col1.classList.add('col');
            let roomTitle = document.createElement('p');
            roomTitle.id = 'roomTitle';
            roomTitle.textContent = room.roomTitle;
            col1.appendChild(roomTitle);
            element.appendChild(col1);

            let col2 = document.createElement('div');
            col2.classList.add('col');
            let fooOutOfBarCorrect = document.createElement('p');
            fooOutOfBarCorrect.id = 'fooOutOfBarCorrect';
            fooOutOfBarCorrect.textContent = `${room.totalPoints}/${room.maxPoints} korrekt`;
            col2.appendChild(fooOutOfBarCorrect);
            element.appendChild(col2);

            let col3 = document.createElement('div');
            col3.classList.add('col');
            let btnGroup = document.createElement('div');
            btnGroup.classList.add('btn-group');
            btnGroup.role = 'group';

            let updateRoom = document.createElement('a');
            updateRoom.classList.add('btn', 'btn-light');
            updateRoom.id = 'updateRoom';
            updateRoom.type = 'button';
            updateRoom.onclick = () => handleLocationChange(`/do-update-room?roomId=${room.roomId}`);
            updateRoom.textContent = 'Bearbeiten';
            btnGroup.appendChild(updateRoom);

            let getQRCode = document.createElement('a');
            getQRCode.classList.add('btn', 'btn-light');
            getQRCode.id = 'getQRCode';
            getQRCode.type = 'button';
            getQRCode.onclick = () => {
                let url = "https://api.qrserver.com/v1/create-qr-code/?size=300x300&data=" + window.location.hostname + "/get-random-question?roomId=" + room.roomId;
                window.open(url, '_blank')
            }
            getQRCode.textContent = 'QR-Code anzeigen';
            btnGroup.appendChild(getQRCode);

            let deleteRoom = document.createElement('a');
            deleteRoom.classList.add('btn', 'btn-danger');
            deleteRoom.id = 'deleteRoom';
            deleteRoom.type = 'button';
            deleteRoom.onclick = () => {
                if (room.active) {
                    Swal.fire({
                        title: 'Bist du sicher?',
                        text: 'Du kannst einen Raum auch de-aktivieren.',
                        showCancelButton: true,
                        cancelButtonText: 'Abbrechen',
                        cancelButtonColor: '#f8f9fa',
                        showDenyButton: true,
                        denyButtonText: 'Deaktivieren',
                        denyButtonColor: '#f8f9fa',
                        confirmButtonText: 'Löschen',
                        confirmButtonColor: '#dc3545',
                        customClass: {
                            cancelButton: 'text-black',
                            denyButton: 'text-black'
                        },
                    }).then((result) => {
                        if (result.isConfirmed) {
                            Swal.fire({
                                title: 'Achtung!',
                                text: 'Damit werden auch alle mit diesem Raum verlinkten Exponate auf "nicht zugeordnet" gesetzt.',
                                showCancelButton: true,
                                cancelButtonText: 'Abbrechen',
                                cancelButtonColor: '#f8f9fa',
                                confirmButtonText: 'Trotzdem löschen',
                                confirmButtonColor: '#dc3545',
                                customClass: {
                                    cancelButton: 'text-black'
                                },
                            }).then((result) => {
                                if (result.isConfirmed) {
                                    handleLocationChange(`delete-room?roomId=${room.roomId}`)
                                }
                            })
                        } else if (result.isDenied) {
                            handleLocationChange(`/deactivate-room?roomId=${question.roomId}`);
                        }
                    })
                } else {
                    Swal.fire({
                        title: 'Achtung!',
                        text: 'Damit werden auch alle mit diesem Raum verlinkten Exponate auf "nicht zugeordnet" gesetzt.',
                        showCancelButton: true,
                        cancelButtonText: 'Abbrechen',
                        cancelButtonColor: '#f8f9fa',
                        confirmButtonText: 'Trotzdem löschen',
                        confirmButtonColor: '#dc3545',
                        customClass: {
                            cancelButton: 'text-black'
                        },
                    }).then((result) => {
                        if (result.isConfirmed) {
                            handleLocationChange(`delete-room?roomId=${room.roomId}`)
                        }
                    })
                }
            };
            deleteRoom.textContent = 'Löschen';
            btnGroup.appendChild(deleteRoom);

            col3.appendChild(btnGroup);
            element.appendChild(col3);

            document.getElementById('list-content-rooms').appendChild(element);
        });
    })
    .catch(error => {
        console.log(error);
        setTimeout(() => {
            window.location.href = '/error.html';
        }, 3000)
    });
