/*
Template Name: Velzon - Admin & Dashboard Template
Author: Themesbrand
Website: https://Themesbrand.com/
Contact: Themesbrand@gmail.com
File: Profile-setting init js
*/


// 프로필 수정
// ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

// Profile Foreground Img
if (document.querySelector("#profile-foreground-img-file-input")) {
    document.querySelector("#profile-foreground-img-file-input").addEventListener("change", function () {
        var preview = document.querySelector(".profile-wid-img");
        var file = document.querySelector(".profile-foreground-img-file-input")
            .files[0];
        var reader = new FileReader();
        reader.addEventListener(
            "load",
            function () {
                preview.src = reader.result;
            },
            false
        );
        if (file) {
            reader.readAsDataURL(file);
        }
    });
}

// Profile Foreground Img
if (document.querySelector("#profile-img-file-input")) {
    document.querySelector("#profile-img-file-input").addEventListener("change", function () {
        var preview = document.querySelector(".user-profile-image");
        var file = document.querySelector(".profile-img-file-input").files[0];
        var reader = new FileReader();
        reader.addEventListener(
            "load",
            function () {
                preview.src = reader.result;
            },
            false
        );
        if (file) {
            reader.readAsDataURL(file);
        }
    });
}


var count = 2;

// var genericExamples = document.querySelectorAll("[data-trigger]");
// for (i = 0; i < genericExamples.length; ++i) {
//     var element = genericExamples[i];
//     new Choices(element, {
//         placeholderValue: "This is a placeholder set in the config",
//         searchPlaceholderValue: "This is a search placeholder",
//         searchEnabled: false,
//     });
// }

function new_link() {
    count++;
    var div1 = document.createElement('div');
    div1.id = count;

    var delLink = '<div class="row"><div class="col-lg-12">' +
        '<div class="mb-3">' +
        '<label for="jobTitle1" class="form-label">Job Title</label>' +
        '<input type="text" class="form-control" id="jobTitle1" placeholder="Job title">' +
        '</div></div>' +
        '<div class="col-lg-6">' +
        '<div class="mb-3">' +
        '<label for="companyName" class="form-label">Company Name</label>' +
        '<input type="text" class="form-control" id="companyName" placeholder="Company name">' +
        '</div>' +
        '</div>' +
        '<div class="col-lg-6">' +
        '<div class="mb-3">' +
        '<label for="choices-single-default3" class="form-label">Experience Years</label>' +
        '<div class="row">' +
        '<div class="col-lg-5">' +
        '<select class="form-control" data-trigger name="choices-single-default3"> ' +
        '<option value="">Select years</option>' +
        '<option value="Choice 1">2001</option>' +
        '<option value="Choice 2">2002</option>' +
        '<option value="Choice 3">2003</option>' +
        '<option value="Choice 4">2004</option>' +
        '<option value="Choice 5">2005</option>' +
        '<option value="Choice 6">2006</option>' +
        '<option value="Choice 7">2007</option>' +
        '<option value="Choice 8">2008</option>' +
        '<option value="Choice 9">2009</option>' +
        '<option value="Choice 10">2010</option>' +
        '<option value="Choice 11">2011</option>' +
        '<option value="Choice 12">2012</option>' +
        '<option value="Choice 13">2013</option>' +
        '<option value="Choice 14">2014</option>' +
        '<option value="Choice 15">2015</option>' +
        '<option value="Choice 16">2016</option>' +
        '<option value="Choice 17">2017</option>' +
        '<option value="Choice 18">2018</option>' +
        '<option value="Choice 19">2019</option>' +
        '<option value="Choice 20">2020</option>' +
        '<option value="Choice 21">2021</option>' +
        '<option value="Choice 22">2022</option>' +
        '</select>' +
        '</div>' +
        '<div class="col-auto align-self-center">to</div>' +
        '<div class="col-lg-5">' +
        '<select class="form-control" data-trigger  name="choices-single-default2">' +
        '<option value="">Select years</option>' +
        '<option value="Choice 1">2001</option>' +
        '<option value="Choice 2">2002</option>' +
        '<option value="Choice 3">2003</option>' +
        '<option value="Choice 4">2004</option>' +
        '<option value="Choice 5">2005</option>' +
        '<option value="Choice 6">2006</option>' +
        '<option value="Choice 7">2007</option>' +
        '<option value="Choice 8">2008</option>' +
        '<option value="Choice 9">2009</option>' +
        '<option value="Choice 10">2010</option>' +
        '<option value="Choice 11">2011</option>' +
        '<option value="Choice 12">2012</option>' +
        '<option value="Choice 13">2013</option>' +
        '<option value="Choice 14">2014</option>' +
        '<option value="Choice 15">2015</option>' +
        '<option value="Choice 16">2016</option>' +
        '<option value="Choice 17">2017</option>' +
        '<option value="Choice 18">2018</option>' +
        '<option value="Choice 19">2019</option>' +
        '<option value="Choice 20">2020</option>' +
        '<option value="Choice 21">2021</option>' +
        '<option value="Choice 22">2022</option>' +
        '</select></div></div></div></div>' +
        '<div class="col-lg-12">' +
        '<div class="mb-3">' +
        '<label for="jobDescription" class="form-label">Job Description</label>' +
        '<textarea class="form-control" id="jobDescription" rows="3" placeholder="Enter description"></textarea>' +
        '</div></div><div class="hstack gap-2 justify-content-end"><a class="btn btn-success" href="javascript:deleteEl(' + count + ')">Delete</a></div></div>';

    div1.innerHTML = document.getElementById('newForm').innerHTML + delLink;

    document.getElementById('newlink').appendChild(div1);
    
    var genericExamples = document.querySelectorAll("[data-trigger]");
    Array.from(genericExamples).forEach(function (genericExamp) {
        var element = genericExamp;
        new Choices(element, {
            placeholderValue: "This is a placeholder set in the config",
            searchPlaceholderValue: "This is a search placeholder",
            searchEnabled: false,
        });
    });
}

function deleteEl(eleId) {
    d = document;
    var ele = d.getElementById(eleId);
    var parentEle = d.getElementById('newlink');
    parentEle.removeChild(ele);
}

/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━프로필 이미지 변경 함수━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/

const defaultImagePath = "/assets/images/profile/none.jpg";
// 이미지 파일 경로 배열
const imagePaths = [
    "/assets/images/profile/none.jpg",
    "/assets/images/profile/boy1.png",
    "/assets/images/profile/boy2.png",
    "/assets/images/profile/girl.png",
    "/assets/images/profile/man1.png",
    "/assets/images/profile/man2.png",
    "/assets/images/profile/man3.png",
    "/assets/images/profile/man4.png",
    "/assets/images/profile/mohican.png",
    "/assets/images/profile/mustache.png",
    "/assets/images/profile/woman1.png",
    "/assets/images/profile/woman2.png",
    "/assets/images/profile/woman3.png",
    "/assets/images/profile/woman4.png",
    "/assets/images/profile/Juicy.png",
    "/assets/images/profile/Juicy-1.png"

];

// 이미지 목록을 동적으로 생성
const imageList = document.getElementById('image-list');
console.log(imageList)

// 프로필 이미지 경로 확인
const profileImage = document.getElementById('profileURLInput');
console.log(profileImage)
if (profileImage) {
    const profileURL = profileImage.getAttribute('src');
    console.log(profileURL)
    if (!profileURL) {
        // profileURL이 없는 경우 기본 이미지로 설정
        profileImage.src = defaultImagePath;
        console.log(defaultImagePath)
    }
}



let selectedImage = null;
let selectedImagePath = null;
// 이미지를 클릭할 때 선택 이벤트를 처리
imagePaths.forEach((imagePath, index) => {
    const imageElement = document.createElement('img');
    imageElement.src = imagePath;
    imageElement.style.width = '100px';
    imageElement.style.height = '100px';

    // 이미지를 클릭할 때 선택 이벤트를 처리
    imageElement.addEventListener('click', () => {
        // 이전에 선택한 이미지의 테두리 제거
        if (selectedImage) {
            selectedImage.style.border = 'none';
        }

        // 현재 선택한 이미지 테두리 추가
        imageElement.style.border = '2px solid #D4F4EC';

        // 선택한 이미지를 저장
        selectedImage = imageElement;

        // 선택한 이미지의 경로를 가져와서 <img> 요소에 설정
        selectedImagePath = imageElement.src;

        const profileImage = document.getElementById('profileURLInput');
        profileImage.src = selectedImagePath;
    });

        imageList.appendChild(imageElement);
});

const confirmSelectionButton = document.getElementById('confirmSelection');
confirmSelectionButton.addEventListener('click', () => {
    const profileURLInput = document.getElementById('profile-img-file-input');

    if (selectedImage) {
        // 이미지가 선택된 경우
        selectedImagePath = selectedImagePath.replace('http://localhost:8086', '');
        profileURLInput.value = selectedImagePath;
    }

    // 추가: 이미지를 클릭하지 않았을 때 기본 이미지로 설정
    if (!selectedImage) {
        selectedImagePath = defaultImagePath;
        const profileImage = document.getElementById('profileURLInput');
        profileImage.src = selectedImagePath;

        const profileURLInput = document.getElementById('profile-img-file-input');
        profileURLInput.value = selectedImagePath;
    }
    // 이미지 경로 확인
    console.log(profileURLInput.value);

    const modifyProfileImageModal = new bootstrap.Modal(document.getElementById('modifyProfileImage'));
    modifyProfileImageModal.hide();
});


/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━아이디 중복검사 함수  ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/

function idCheck() {
    var d = document;
    var newid = d.getElementById("Id").value;

    var xhr = new XMLHttpRequest();
    console.log(newid);

    xhr.open("POST", "http://localhost:8086/api/idcheck", true);
    xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8"); // JSON 요청 헤더 설정

    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4) {
            if (xhr.status == 200) {
                // 사용 가능한 아이디
                var errorMessage = "사용 가능한 id입니다.";
                var errorDiv = document.getElementById("id-error-message");
                errorDiv.innerHTML = errorMessage;
                errorDiv.style.color = "green"; // 빨간색으로 설정
                errorDiv.style.fontSize = "small"; // 원하는 글꼴 크기 설정
                console.log("사용 가능한 Id입니다.");
            } else if (xhr.status == 409) {
                // 중복된 아이디
                var errorMessage = "중복된 id입니다.";
                var errorDiv = document.getElementById("id-error-message");
                errorDiv.innerHTML = errorMessage;
                errorDiv.style.color = "red"; // 빨간색으로 설정
                errorDiv.style.fontSize = "small"; // 원하는 글꼴 크기 설정
                console.log("중복된 닉네임입니다.");
            } else {
                console.log("다른 오류 발생: " + xhr.status);
            }
        }
    };
    // 데이터를 JSON 형태로 보내기
    xhr.send(JSON.stringify({ "newid": newid }));
}

/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━닉네임 중복검사 함수  ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
function nickNameCheck() {
    var d = document;
    var newnick = d.getElementById("lastnameInput").value;
    var xhr = new XMLHttpRequest();
    console.log(newnick);

    xhr.open("POST", "http://localhost:8086/api/nickcheck", true);
    xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8"); // JSON 요청 헤더 설정

    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4) {
            if (xhr.status == 200) {
                // 사용 가능한 닉네임
                var errorMessage = "사용 가능한 닉네임입니다.";
                var errorDiv = document.getElementById("nick-error-message");
                errorDiv.innerHTML = errorMessage;
                errorDiv.style.color = "green"; // 빨간색으로 설정
                errorDiv.style.fontSize = "small"; // 원하는 글꼴 크기 설정
                console.log("사용 가능한 닉네임입니다.");

            } else if (xhr.status == 409) {
                // 중복된 닉네임
                var errorMessage = "중복된 닉네임입니다..";
                var errorDiv = document.getElementById("nick-error-message");
                errorDiv.innerHTML = errorMessage;
                errorDiv.style.color = "red"; // 빨간색으로 설정
                errorDiv.style.fontSize = "small"; // 원하는 글꼴 크기 설정
                console.log("중복된 닉네임입니다.");
            } else {
                console.log("다른 오류 발생: " + xhr.status);
            }
        }
    };
    // 데이터를 JSON 형태로 보내기
    xhr.send(JSON.stringify({ "newnick": newnick }));
}

/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━이메일 인증 함수  ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
function sendEmail() {
    var d = document;
    var email = d.getElementById("emailInput").value;
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "http://localhost:8086/api/sendmail");
    xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");

    // 서버로 전송할 데이터 준비
    var data = { "checkemail": email };
    var jdata = JSON.stringify(data);

    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4) {
            if (xhr.status == 200) {
                // 이메일 전송 성공 시, 인증 코드 입력 창 표시
                document.getElementById("verificationCodeDiv").style.display = "flex";
                document.querySelector("#verificationCodeDiv button").style.display = "block";
                alert("이메일이 전송되었습니다.")
                console.log("이메일이 전송되었습니다.");
            } else if (xhr.status == 409) {
                console.log("409 오류." + xhr.status);
                alert("이메일 전송 실패");
            } else {
                console.log("다른 오류 발생: " + xhr.status);
            }
        }
    };

    xhr.send(jdata);
}

function verifyCode() {
    var d = document;
    var verificationCode = d.getElementById("verificationCodeInput").value;
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "http://localhost:8086/api/verifycode");
    xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");

    // 서버로 전송할 데이터 준비
    var data = { "verificationCode": verificationCode };
    var jdata = JSON.stringify(data);

    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4) {
            if (xhr.status == 200) {
                // 인증 코드 확인 성공 시 업데이트 로직 수행
                console.log("인증 코드 확인 성공.");
                alert("인증이 완료되었습니다.");
                document.getElementById('isVerificationComplete').value=true;

            } else if (xhr.status == 409) {
                console.log("409 오류." + xhr.status);
                document.getElementById('isVerificationComplete').value=false;
                alert("인증 코드 확인 실패");
            } else {
                document.getElementById('isVerificationComplete').value=false;
                console.log("다른 오류 발생: " + xhr.status);

            }
        }
    };

    xhr.send(jdata);
}
/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━프로필 변경 확인 함수  ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/

function enableProfileModification() {
    var isVerificationCompleteInput = document.getElementById('isVerificationComplete');
    var errorDiv = document.getElementById('nick-error-message'); // Assuming you have an 'errorDiv' for the nickname error message
    var errorMessage = errorDiv.innerHTML;

    if (errorDiv.style.color === 'red') {
        alert('중복된 닉네임입니다. 수정할 수 없습니다.');
        return; // Prevent further execution of the function
    }

    if (isVerificationCompleteInput.value === 'true') {
        if (confirm('정말 수정하시겠습니까?')) {
            // 사용자가 확인 버튼을 누르면 폼이 제출됩니다.
            document.getElementById('profileForm').submit();
        }
    } else {
        alert('이메일이 인증되지 않았습니다.');
    }
}
/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━비밀번호 변경 함수 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
var firstStep = false;
var secondStep = false;
function changePassword() {
    var d = document;
    oldpassword = d.getElementById("oldpasswordInput").value;
    oldpasswordCheck = d.getElementById("oldpasswordCheck").value;
    idForChangePassword = d.getElementById("idForChangePassword").value;
    newpassword = d.getElementById("newpasswordInput").value;
    confirmpassword = d.getElementById("confirmpasswordInput").value;

    if (oldpassword === oldpasswordCheck) {
        console.log("현재 비밀번호 일치")
        firstStep = true;
    } else {
        console.log("현재 비밀번호 불일치")
        firstStep = false;
        alert("기존 비밀번호 입력이 잘못되었습니다.");
    }

    if (firstStep == true && newpassword === confirmpassword) {
        console.log("새 비밀번호 일치 ")
        secondStep = true;
    } else {
        console.log("새 비밀번호 불일치")
        secondStep = false;
        alert("새 비밀번호 입력이 잘못되었습니다.");
    }

    if (firstStep == true && secondStep == true) {
        console.log("비밀번호 변경 가능");

        // 사용자에게 확인 메시지 표시
        var userConfirmed = confirm("비밀번호를 변경 하시겠습니까?");

        if (userConfirmed) {
            // 사용자가 "확인"을 클릭한 경우에만 비밀번호 변경 코드 실행
            // JSON 데이터 생성
            var data = {
                "idForChangePassword": idForChangePassword,
                "confirmpassword": confirmpassword
            };

            // JSON 데이터를 문자열로 변환
            var jdata = JSON.stringify(data);

            // XMLHttpRequest를 사용하여 서버로 전송
            var xhr = new XMLHttpRequest();
            xhr.open("POST", "http://localhost:8086/api/changepassword");
            xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
            xhr.onreadystatechange = function () {
                if (xhr.readyState == 4) {
                    if (xhr.status == 200) {
                        console.log("비밀번호가 변경되었습니다.");
                        alert("비밀번호가 변경되었습니다.");
                    } else if (xhr.status == 409) {
                        console.log("비밀번호 변경 실패: 409 오류." + xhr.status);

                        alert("비밀번호 변경에 실패했습니다.");
                    } else {
                        console.log("다른 오류 발생: " + xhr.status);
                    }
                }
            };

            xhr.send(jdata);
        } else {
            console.log("사용자가 비밀번호 변경을 취소했습니다.");
        }
    } else {
        console.log("변경 전 비밀번호와 같습니다.");
    }
}

/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━소셜 유저는 인증 X ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
document.addEventListener('DOMContentLoaded', function () {
    var ownsns = document.getElementById('sns').value;
    var socialdiv = document.getElementById('socialdiv');
    var emailInput = document.getElementById('emailInput');
    var passcodeElement = document.getElementById('isVerificationComplete');

    if (ownsns != null && ownsns.trim() !== "") {
        // SNS 정보가 존재하면 버튼을 제거합니다.
        console.log(ownsns);
        socialdiv.style.display = 'none';
        emailInput.readOnly = true;

        // 'isVerificationComplete' 요소의 값을 'true'로 설정합니다.
        passcodeElement.value = 'true';
        var passcode = passcodeElement.value;
        console.log(passcode);
    }
});


/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━회원탈퇴 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/

document.getElementById('deleteLink').addEventListener('click', function(event) {
    event.preventDefault(); // 기본 클릭 동작을 막음

    if (confirm('정말로 탈퇴하시겠습니까?')) {
        var userNo = document.getElementById('userNoFordelete').value; // userNo 값을 가져옴

        var xhr = new XMLHttpRequest();
        xhr.open('POST', '/api/delete', true);
        xhr.setRequestHeader('Content-Type', 'application/json');

        xhr.onload = function() {
            if (xhr.status === 200) {
                alert('탈퇴되었습니다.');
                window.location.href = '/users/signin';
            } else {
                alert('탈퇴 요청에 실패했습니다.');
            }
        };

        var requestData = JSON.stringify({ userNo: userNo });
        xhr.send(requestData);
    }
});

/*━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━회원가입 오류메세지 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━*/
function signup() {
    var d = document;
    var newnick = d.getElementById("lastnameInput").value;

    var xhr = new XMLHttpRequest();
    xhr.open("POST", "http://localhost:8086/api/signup", true);
    xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");

    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4) {
            if (xhr.status == 200) {
                // Sign Up 성공
                var form = document.getElementById("signupForm"); // 폼 요소를 가져옵니다.
                form.submit();
                window.location.href = "/users/signup"; // 원하는 페이지로 리디렉션
            } else if (xhr.status == 400) {
                // 서버에서 반환된 에러 메시지 표시
                var errorMessages = JSON.parse(xhr.responseText);
                var errorMessageText = errorMessages.join('<br>');
                document.getElementById("errorMessages").innerHTML = errorMessageText;

                $('#errorModal').modal('show'); // 모달을 표시
            } else {
                console.log("다른 오류 발생: " + xhr.status);
            }
        }
    };

    var requestBody = JSON.stringify({ "newnick": newnick });
    xhr.send(requestBody);
}