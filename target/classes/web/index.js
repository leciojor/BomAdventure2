import Scripture from './scripture.js';

document.querySelector('.large-button').addEventListener('click', function() {
    var audio = new Audio('https://raw.githubusercontent.com/leciojor/BOMadventure/main/booksSong.mp3');
    audio.loop = true;
    audio.play();

var popup = document.getElementById('popup');
  popup.style.display = 'block';
  popup.style.opacity = 0;
  var opacity = 0;
  var fadeInInterval = setInterval(function() {
    opacity += 0.1;
    popup.style.opacity = opacity;
    if (opacity >= 1) {
      clearInterval(fadeInInterval);
    }
  }, 100);
});

quizRequest();

function rewritePopupContent(popupContent, num, userInput){
    let newStr = popupContent.substring(0, num) + userInput + popupContent.substring(num);
    popupContent.textContent = newStr;
}



function inputUpdates(popupContent, indexes_arrays_remove){
    console.log("Got into the beggining of inputUpdates");
    console.log("popupContent lenght "  + popupContent.length);
    let num = 1;

    for (let i = 0; i < popupContent.length;) {
        console.log("Got into inputUpdates loop");
        if (popupContent[i] === '_') {
                var addText = document.getElementById('add-text');
                addText.textContent = "Give answer for empty space ";
                addText.style.display = 'block';
                console.log("Text to add: " + addText);

                addText.textContent += num.toString();
                var inputField = document.getElementById('input');
                inputField.style.display = 'block';

                inputField.addEventListener('change', function() {

                                var userInput = inputField.value;
//                                while (userInput.trim() === '') {
//                                        userInput = inputField.value;
//                                    }
                                console.log("User input:", userInput);

                            });

                                if (indexes_arrays_remove[num - 1][1].toLowerCase() === userInput.toLowerCase()){
                                    var audio = new Audio('https://raw.githubusercontent.com/leciojor/BOMadventure/main/interface-124464.mp3');
                                    audio.play();
                                    rewritePopupContent(popupContent, num, userInput);
                                    num++;
                                    i++;
                                    console.log("incresed i empty right");
                                }
                                else{
                                    var audio = new Audio('https://raw.githubusercontent.com/leciojor/BOMadventure/main/wrong-47985.mp3');
                                    audio.play();
                                }
        }
        else{
            console.log("incresed i non empty");
            i++;
        }
    }

}


function quizRequest() {



function printArrayStrings(array, outputDiv) {
    outputDiv.innerHTML = "";

    if (array === null || array.length === 0) {
        outputDiv.textContent += "_";
    } else {
        array.forEach(string => {
            if (string === null) {
                outputDiv.textContent += " _ ";
            } else {
                outputDiv.textContent += string + " ";
            }
        });
    }
}


function printSetWords(wordsSet, outputDiv) {
    wordsSet.forEach(function(word) {
        outputDiv.textContent += "'"  + word + "'" + " - ";
    });
}




  var xhr = new XMLHttpRequest();
  xhr.open('GET', 'http://localhost:8080/quiz', true);
  xhr.send();

  xhr.onreadystatechange = function() {
    if (xhr.readyState == XMLHttpRequest.DONE) {
        var response = JSON.parse(xhr.responseText);
        var scripture = new Scripture(
                            response.scripture_name,
                            response.scripture_blancks,
                            response.scripture_full,
                            response.indexes_arrays_remove,
                            response.level,
                            response.words_set
                        );

        let level = scripture.level.toString();

        console.log("Level: " + level);
        console.log("Scripture Name: ", scripture.scripture_name);
        var popupTitle = document.getElementById('popup-title');
        popupTitle.textContent = scripture.scripture_name;
        var popupContent = document.getElementById('popup-text');
        if (response.scripture_blancks.length == 0){
                    console.log("is empty");
        }
        var subTitleContent = document.getElementById('popup-subTitle');
        subTitleContent.textContent = "Level " + level;
        printArrayStrings(response.scripture_blancks, popupContent);
        var possibleWords = document.getElementById('possible-words');
        printSetWords(response.words_set, possibleWords);

        console.log("Got into the end of get HTTP requests");
        inputUpdates(popupContent.textContent, scripture.indexes_arrays_remove);
    }
  };

}

document.getElementById('reset-button').addEventListener('click', function() {
    var resetXhr = new XMLHttpRequest();
    resetXhr.open('POST', 'http://localhost:8080/reset', true);
    resetXhr.send();

    resetXhr.onreadystatechange = function() {
        if (resetXhr.readyState == XMLHttpRequest.DONE) {
            if (resetXhr.status == 200) {
                var response = JSON.parse(resetXhr.responseText);
                var newLevel = response.level;
                console.log("Reset successful. New level:", newLevel);
                document.getElementById('popup-subTitle').textContent = "Level " + newLevel;
                quizRequest();
            } else {
                console.error("Reset failed:", resetXhr.status);
            }
        }
    };
});
