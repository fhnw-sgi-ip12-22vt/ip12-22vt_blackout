fetch('all-questions.json')
    .then(response => response.json())
    .then(data => {
        if (!data.questions) return;
        let questions = data.questions;

        if (questions.length > 0) {
            document.getElementById('no-content').innerHTML = '';
        }

        questions.forEach(question => {
            let element = document.createElement('div');
            element.classList.add('row', 'gy-4', 'row-cols-1', 'row-cols-md-2', 'row-cols-xl-3');
            element.style.marginBottom = '10px';

            let col1 = document.createElement('div');
            col1.classList.add('col');
            let questionStringDE = document.createElement('p');
            questionStringDE.id = 'questionStringDE';
            questionStringDE.textContent = question.questionStringDE;
            col1.appendChild(questionStringDE);
            element.appendChild(col1);

            let col2 = document.createElement('div');
            col2.classList.add('col');
            let fooOutOfBarCorrect = document.createElement('p');
            fooOutOfBarCorrect.id = 'fooOutOfBarCorrect';
            fooOutOfBarCorrect.textContent = `${question.totalPoints}/${question.maxPoints} Pkt.`;
            col2.appendChild(fooOutOfBarCorrect);
            element.appendChild(col2);

            let col3 = document.createElement('div');
            col3.classList.add('col');
            let btnGroup = document.createElement('div');
            btnGroup.classList.add('btn-group');
            btnGroup.role = 'group';

            let updateQuestion = document.createElement('a');
            updateQuestion.classList.add('btn', 'btn-light');
            updateQuestion.id = 'updateQuestion';
            updateQuestion.type = 'button';
            updateQuestion.onclick = () => handleLocationChange(`/do-update-question?questionId=${question.questionId}`);
            updateQuestion.textContent = 'Bearbeiten';
            btnGroup.appendChild(updateQuestion);

            if (question.active) {
                let deactivateQuestion = document.createElement('a');
                deactivateQuestion.classList.add('btn', 'btn-light');
                deactivateQuestion.id = 'deactivateQuestion';
                deactivateQuestion.type = 'button';
                deactivateQuestion.onclick = () => handleLocationChange(`/deactivate-question?questionId=${question.questionId}`);
                deactivateQuestion.textContent = 'Deaktivieren';
                btnGroup.appendChild(deactivateQuestion);
            } else {
                let activateQuestion = document.createElement('a');
                activateQuestion.classList.add('btn', 'btn-light');
                activateQuestion.id = 'activateQuestion';
                activateQuestion.type = 'button';
                activateQuestion.onclick = () => handleLocationChange(`/activate-question?questionId=${question.questionId}`);
                activateQuestion.textContent = 'Aktivieren';
                btnGroup.appendChild(activateQuestion);
            }

            let deleteQuestion = document.createElement('a');
            deleteQuestion.classList.add('btn', 'btn-danger');
            deleteQuestion.id = 'deleteQuestion';
            deleteQuestion.type = 'button';
            deleteQuestion.onclick = () => {
                if (question.active) {
                    Swal.fire({
                        title: 'Bist du sicher?',
                        text: 'Du kannst eine Frage auch de-aktivieren.',
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
                                text: 'Damit werden auch alle mit dieser Frage zusammenhängenden Statistiken gelöscht!',
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
                                    handleLocationChange(`delete-question?questionId=${question.questionId}`)
                                }
                            })
                        } else if (result.isDenied) {
                            handleLocationChange(`/deactivate-question?questionId=${question.questionId}`);
                        }
                    })
                } else {
                    Swal.fire({
                        title: 'Achtung!',
                        text: 'Damit werden auch alle mit dieser Frage zusammenhängenden Statistiken gelöscht!',
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
                            handleLocationChange(`delete-question?questionId=${question.questionId}`)
                        }
                    })
                }
            };
            deleteQuestion.textContent = 'Löschen';
            btnGroup.appendChild(deleteQuestion);

            col3.appendChild(btnGroup);
            element.appendChild(col3);

            document.getElementById('list-content-questions').appendChild(element);
        });
    })
    .catch(error => {
        console.log(error);
        setTimeout(() => {
            window.location.href = '/error.html';
        }, 3000)
    });
