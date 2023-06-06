fetch('exponats.json')
    .then(response => response.json())
    .then(data => {
        if (!data.exponats) return;
        let exponats = data.exponats;

        if (exponats.length > 0) {
            document.getElementById('no-content').innerHTML = '';
        }

        exponats.forEach(exponat => {
            let element = document.createElement('div');
            element.classList.add('row', 'gy-4', 'row-cols-1', 'row-cols-md-2', 'row-cols-xl-3');
            element.style.marginBottom = '10px';

            let col1 = document.createElement('div');
            col1.classList.add('col');
            let exponatTitle = document.createElement('p');
            exponatTitle.id = 'exponatTitle';
            exponatTitle.textContent = exponat.exponatTitle;
            col1.appendChild(exponatTitle);
            element.appendChild(col1);

            let col2 = document.createElement('div');
            col2.classList.add('col');
            let fooOutOfBarCorrect = document.createElement('p');
            fooOutOfBarCorrect.id = 'fooOutOfBarCorrect';
            fooOutOfBarCorrect.textContent = `${exponat.totalPoints}/${exponat.maxPoints} korrekt`;
            col2.appendChild(fooOutOfBarCorrect);
            element.appendChild(col2);

            let col3 = document.createElement('div');
            col3.classList.add('col');
            let btnGroup = document.createElement('div');
            btnGroup.classList.add('btn-group');
            btnGroup.role = 'group';

            let updateExponat = document.createElement('a');
            updateExponat.classList.add('btn', 'btn-light');
            updateExponat.id = 'updateExponat';
            updateExponat.type = 'button';
            updateExponat.onclick = () => handleLocationChange(`/do-update-exponat?exponatId=${exponat.exponatId}`);
            updateExponat.textContent = 'Bearbeiten';
            btnGroup.appendChild(updateExponat);

            // if (exponat.active) {
            //     let deactivateExponat = document.createElement('a');
            //     deactivateExponat.classList.add('btn', 'btn-light');
            //     deactivateExponat.id = 'deactivateExponat';
            //     deactivateExponat.type = 'button';
            //     deactivateExponat.onclick = () => handleLocationChange(`/deactivate-exponat?exponatId=${exponat.exponatId}`);
            //     deactivateExponat.textContent = 'Deaktivieren';
            //     btnGroup.appendChild(deactivateExponat);
            // } else {
            //     let activateExponat = document.createElement('a');
            //     activateExponat.classList.add('btn', 'btn-light');
            //     activateExponat.id = 'activateExponat';
            //     activateExponat.type = 'button';
            //     activateExponat.onclick = () => handleLocationChange(`/activate-exponat?exponatId=${exponat.exponatId}`);
            //     activateExponat.textContent = 'Aktivieren';
            //     btnGroup.appendChild(activateExponat);
            // }

            let deleteExponat = document.createElement('a');
            deleteExponat.classList.add('btn', 'btn-danger');
            deleteExponat.id = 'deleteExponat';
            deleteExponat.type = 'button';
            deleteExponat.onclick = () => {
                if (exponat.active) {
                    Swal.fire({
                        title: 'Bist du sicher?',
                        text: 'Du kannst ein Exponat auch de-aktivieren.',
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
                                text: 'Damit werden auch alle mit diesem Exponat verlinkten Fragen auf nicht zugeordnet gesetzt!',
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
                                    handleLocationChange(`delete-exponat?exponatId=${exponat.exponatId}`)
                                }
                            })
                        } else if (result.isDenied) {
                            handleLocationChange(`/deactivate-exponat?exponatId=${exponat.exponatId}`);
                        }
                    })
                } else {
                    Swal.fire({
                        title: 'Achtung!',
                        text: 'Damit werden auch alle mit diesem Exponat verlinkten Fragen auf nicht zugeordnet gesetzt!',
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
                            handleLocationChange(`delete-exponat?exponatId=${exponat.exponatId}`)
                        }
                    })
                }
            };
            deleteExponat.textContent = 'Löschen';
            btnGroup.appendChild(deleteExponat);

            col3.appendChild(btnGroup);
            element.appendChild(col3);

            document.getElementById('list-content-exponats').appendChild(element);
        });
    })
    .catch(error => {
        console.log(error);
        setTimeout(() => {
            window.location.href = '/error.html';
        }, 3000)
    });
