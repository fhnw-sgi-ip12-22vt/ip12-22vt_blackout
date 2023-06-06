fetch('questions.json')
    .then(response => response.json())
    .then(async (data) => {
        if (!data.questions) return;

        let exponats;
        try {
            const response1 = await fetch('exponats.json');
            const data1 = await response1.json();
            if (!data1.exponats) return;
            exponats = data1.exponats;
        } catch (error) {
            console.log(error);
        }

        let rooms;
        try {
            const response2 = await fetch('rooms.json');
            const data2 = await response2.json();
            if (!data2.rooms) return;
            rooms = data2.rooms;
        } catch (error) {
            console.log(error);
        }

        var totalPoints = 0;

        let questionInfos = window.location.search
            .split('?questionsInfo=')[1]
            .split('&')[0]
            .split(';')
            .filter(info => info !== '');

        questionInfos.forEach(questionInfo => {
            let [questionId, answerIds, points] = questionInfo.split('-');
            questionId = Number(questionId);

            let answersArray;
            if (answerIds.includes(',')) {
                answersArray = answerIds.split(',').map(Number);
            } else {
                answersArray = [Number(answerIds)];
            }

            totalPoints += parseInt(points);

            //
            var myHeaders = new Headers();
            myHeaders.append("Content-Type", "application/json");

            var raw = JSON.stringify({
                "date": getQueryParameterValue("date"),
                "questionId": questionId,
                "points": totalPoints
            });

            var requestOptions = {
                method: 'POST',
                headers: myHeaders,
                body: raw,
                redirect: 'follow'
            };

            fetch("https://blackout--blackoutprimeo.repl.co/result", requestOptions)
                .then(response => response.text())
                .then(result => console.log(result))
                .catch(error => console.log('error', error));
            //

            const question = data.questions.find(q => q.questionId === questionId);
            const solutionArray = question.solutionArray;

            let questionString = "";
            let answerStrings = [];
            let explanationString = "";

            if (getQueryParameterValue("language") == "en") {
                questionString = question.questionStringEN;
                answerStrings = question.answerStringsEN;
                explanationString = question.explanationStringEN;

                document.getElementById("title").innerHTML = "Here's what you know about energy";
                document.getElementById("subtitle").innerHTML = "Below you can see how well you did in each question and in which room you can learn more.";
                document.getElementById("greeting").innerHTML = "See you soon again at Primeo Energie AG!";
            }

            if (getQueryParameterValue("language") == "de") {
                questionString = question.questionStringDE;
                answerStrings = question.answerStringsDE;
                explanationString = question.explanationStringDE;
            }

            if (getQueryParameterValue("language") == "fr") {
                questionString = question.questionStringFR;
                answerStrings = question.answerStringsFR;
                explanationString = question.explanationStringFR;

                document.getElementById("title").innerHTML = "Voilà où en est ta connaissance de l'énergie";
                document.getElementById("subtitle").innerHTML = "Tu peux voir ci-dessous les résultats que tu as obtenus à chaque question et dans quelle salle tu peux en savoir plus.";
                document.getElementById("greeting").innerHTML = "À bientôt chez Primeo Energie AG !";
            }

            if (getQueryParameterValue("language") == "it") {
                questionString = question.questionStringIT;
                answerStrings = question.answerStringsIT;
                explanationString = question.explanationStringIT;

                document.getElementById("title").innerHTML = "Questo è lo stato delle vostre conoscenze energetiche";
                document.getElementById("subtitle").innerHTML = "Qui di seguito potete vedere quanto siete stati bravi in ogni domanda e in quale stanza potete trovare maggiori informazioni.";
                document.getElementById("greeting").innerHTML = "Ci rivediamo presto alla Primeo Energie AG!";
            }

            let imageUrl = question.imgUrl && question.imgUrl.trim() !== '' ? question.imgUrl : 'assets/img/kulana.png';

            let cardContent = `<div class='card border-0 shadow-none' style='border-color: #FFD200 !important;'>
            <img id='image' class='card-img-top w-100 d-block border rounded-circle border-0 fit-cover' style='height: 420px;' src='${imageUrl}'>
            <div class='card-body p-4'>
            <h5 class='card-title' style='min-height: 72px;'>${questionString}</h5>`;

            answerStrings.forEach((answer, answerIndex) => {
                let answerClass = 'card-text';
                let answerStyle = 'min-height: 48px;padding: 10px;';
                if (answersArray.includes(answerIndex)) {
                    if (solutionArray.includes(answerIndex)) {
                        answerStyle += 'background-color: #FFD200 !important;'
                        answerClass += ' border rounded-pill border-3 border-success';
                    } else {
                        answerClass += ' border rounded-pill border-3 border-danger';
                    }
                } else {
                    if (solutionArray.includes(answerIndex)) {
                        answerStyle += 'background-color: #FFD200 !important;'
                        answerClass += ' border rounded-pill border-3 border-success';
                    }
                }
                cardContent += `<p class='${answerClass}' style='${answerStyle}'>${answer}</p>`;
            });

            let exponat = exponats.filter(exponat => {
                return exponat.exponatId == question.exponatId;
            })
            let room = rooms.filter(room => {
                return room.roomId == exponat[0].roomId;
            })

            cardContent += `</div>`;
            cardContent += `<p class='card-text' style='min-height: 48px;padding: 3px;'>${explanationString}</p>`;
            cardContent += `<h4 class='card-title'>${exponat[0].exponatTitle}</h4>`;
            cardContent += `<p class='card-text' style='min-height: 72px;'>${exponat[0].exponatDescription}</p>`;
            cardContent += `<div class='text-start d-flex'><div>`;
            cardContent += `<p class='text-muted mb-0'>${room[0].roomTitle}</p>`;
            cardContent += `<p class='text-muted mb-0'>${room[0].roomDescription}</p>`;
            cardContent += `</div></div></div></div>`;

            let card = document.createElement('div');
            card.className = 'col-xl-4';
            card.innerHTML = cardContent;
            document.getElementById('report-content').appendChild(card);
        });

        document.getElementById("loader").style.display = "none";

        updateBattery(totalPoints, questionInfos.length * 3);
    })
    .catch(error => {
        console.log(error);

        setTimeout(() => {
            window.location.href = '/error.html';
        }, 3000)
    });

function updateBattery(totalPoints, maxPoints) {
    let batteryLevel = document.querySelector('.battery-level');
    let batteryText = document.querySelector('.battery-text');

    for (let i = 0; i <= totalPoints; i++) {
        setTimeout(() => {
            let currentFillPercentage = i / maxPoints;
            batteryLevel.style.transform = `scaleX(${currentFillPercentage})`;
            batteryText.textContent = `${i}/${maxPoints} Pkt.`;

            if (currentFillPercentage <= 1 / 3) {
                batteryLevel.style.backgroundColor = '#dc3545';
            } else if (currentFillPercentage <= 2 / 3) {
                batteryLevel.style.backgroundColor = '#ff8800';
            } else {
                batteryLevel.style.backgroundColor = '#28a745';
            }
        }, i * 300);
    }
}

function getQueryParameterValue(key) {
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    return urlParams.get(key);
}