//서버의 세션 가져오기
let currentSession;
function getSession(){
    const xhr = new XMLHttpRequest();

    xhr.open('GET','/getsession');
    xhr.send();
    xhr.onload = function() {
        if(xhr.status === 200 || xhr.status === 201){
            const session = JSON.parse(xhr.response);
            console.log("▼ 현재 세션 ▼")
            console.log(session)

            currentSession = session;
        } else {
            console.error('오류1',xhr.status)
            console.error('오류2',xhr.response)
        }
    }
}
window.addEventListener('load', getSession);
//━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━//
//검색 버튼
const search = function(){
    const xhr = new XMLHttpRequest();   //비동기 통신 객체 생성
    var projectNo = document.getElementById('projectNo').value
    console.log(projectNo)
    let condition = document.getElementById('condition').value
    console.log('현재 선택된 검색 조건 : '+condition)
    let word = document.getElementById('search-word').value;
    console.log(word)
    if(condition != 'all') {
        if (word.trim().length == 0) {
            alert("검색어를 1글자 이상 입력해주세요.\n※ 검색어는 공백이 될 수 없습니다.")
            return;
        }
    } else {
        word = '전체검색'
    }
    xhr.open('GET','/project/RnR/search/'+projectNo+'/'+condition+'/'+word);    //전송 보낼 준비 (url과 method)
    xhr.send();
    xhr.onload=function(){
        if(xhr.status === 200 || xhr.status ===201){
            const searchDTO = JSON.parse(xhr.response)

            const teamTodoList = searchDTO.teamTodoList;
            const usersList = searchDTO.usersList;

            console.log(teamTodoList)
            console.log(usersList)

            makeList(teamTodoList,usersList)  //makeList 함수는 아래에 있음
        } else {
            console.error('오류1',xhr.status)
            console.error('오류2',xhr.response)
        }
    }
}

document.querySelector('#search').addEventListener('click',search);

//응답받은 To-do 목록을 태그로 출력하는 함수
const makeList = function (teamTodoList,usersList){
    document.querySelector('tbody').innerHTML =''

    let count = 0;

    teamTodoList.forEach(item => {    //배열에서 하나 가져온 member
        const $tr = document.createElement("tr");

        let status;
        if(item.status == 0)
            status = `<span class="badge badge-soft-info" id="currentStatus${count}">진행중</span>`;
        else if(item.status == 1)
            status = `<span class="badge badge-soft-success" id="currentStatus${count}">작업 완료</span>`;
        else if(item.status == 2)
            status = `<span class="badge badge-soft-primary" id="currentStatus${count}">작업 예정</span>`;
        else if(item.status == 3)
            status = `<span class="badge badge-soft-warning" id="currentStatus${count}">검토 요청</span>`;
        else if(item.status == 4)
            status = `<span class="badge badge-soft-danger" id="currentStatus${count}">오류 수정</span>`;

        let statusButton;
        if(item.userNo == currentSession.userNo)
            statusButton = "<div class='avatar-group-item'>\n" +
                `    <a href='javascript:changeStatus(1,${count})' class='d-inline-block'>\n` +
                "        <img src='/assets/images/status/green.png' class='rounded-circle avatar-xxs'>\n" +
                "    </a>\n" +
                "</div>\n" +
                "<div class='avatar-group-item'>\n" +
                `    <a href='javascript:changeStatus(4,${count})' class='d-inline-block'>\n` +
                "        <img src='/assets/images/status/red.png' class='rounded-circle avatar-xxs'>\n" +
                "    </a>\n" +
                "</div>\n" +
                "<div class='avatar-group-item'>\n" +
                `    <a href='javascript:changeStatus(2,${count})' class='d-inline-block'>\n` +
                "        <img src='/assets/images/status/indigo.png' class='rounded-circle avatar-xxs'>\n" +
                "    </a>\n" +
                "</div>\n" +
                "<div class='avatar-group-item'>\n" +
                `    <a href='javascript:changeStatus(0,${count})' class='d-inline-block'>\n` +
                "        <img src='/assets/images/status/blue.png' class='rounded-circle avatar-xxs'>\n" +
                "    </a>\n" +
                "</div>\n" +
                "<div class='avatar-group-item'>\n" +
                `    <a href='javascript:changeStatus(3,${count})' class='d-inline-block'>\n` +
                "        <img src='/assets/images/status/yellow.png' class='rounded-circle avatar-xxs'>\n" +
                "    </a>\n" +
                "</div>";
        else
            statusButton = "<div class='avatar-group-item'>\n" +
                "    <a class='d-inline-block'>\n" +
                "        <img src='/assets/images/status/pastelgreen.png' class='rounded-circle avatar-xxs gu-unselectable'>\n" +
                "    </a>\n" +
                "</div>\n" +
                "<div class='avatar-group-item'>\n" +
                "    <a class='d-inline-block'>\n" +
                "        <img src='/assets/images/status/pastelred.png' class='rounded-circle avatar-xxs gu-unselectable'>\n" +
                "    </a>\n" +
                "</div>\n" +
                "<div class='avatar-group-item'>\n" +
                "    <a class='d-inline-block'>\n" +
                "        <img src='/assets/images/status/pastelindigo.png' class='rounded-circle avatar-xxs gu-unselectable'>\n" +
                "    </a>\n" +
                "</div>\n" +
                "<div class='avatar-group-item'>\n" +
                "    <a class='d-inline-block'>\n" +
                "        <img src='/assets/images/status/pastelblue.png' class='rounded-circle avatar-xxs gu-unselectable'>\n" +
                "    </a>\n" +
                "</div>\n" +
                "<div class='avatar-group-item'>\n" +
                "    <a class='d-inline-block'>\n" +
                "        <img src='/assets/images/status/pastelyellow.png' class='rounded-circle avatar-xxs gu-unselectable'>\n" +
                "    </a>\n" +
                "</div>";

        const $temp=
            `<td class="fw-medium">${count+1}</td>
             <td class="fw-medium">${item.title}</td>
             <td class="fw-medium">${item.description}</td>
             <td class="fw-medium">${item.todoDate}</td>
             <td class="fw-medium">${item.dueDate}</td>
             <td class="fw-medium">${item.endDate}</td>
             <td class="fw-medium">
                <img class="rounded-circle header-profile-user"
                    src="${usersList[count].profileURL}" alt="Header Avatar">
                ${usersList[count].nickName}
             </td>
             <td class="fw-medium">${status}</td>
             <td class="fw-medium">
                <div class="avatar-group flex-nowrap">
                    ${statusButton}
                </div>
             </td>
             <td class="fw-medium">
                <ul class="pagination pagination-separated pagination-sm mb-0">
                    <li class="page-item">
                        <a href="#" class="page-link">
                            댓글
                        </a>
                    </li>
                </ul>
             </td>`;
        $tr.innerHTML=$temp;
        document.querySelector('tbody').appendChild($tr);
        count++;
    });
}

//status 변경하는 함수
function changeStatus(s,c){
    var status = document.getElementById('currentStatus'+c)

    switch (s){
        case 0 :
            status.classList = 'badge badge-soft-info';
            status.textContent = '진행중';
            return;
        case 1:
            status.classList = 'badge badge-soft-success';
            status.textContent = '작업 완료';
            return;
        case 2:
            status.classList = 'badge badge-soft-primary';
            status.textContent = '작업 예정';
            return;
        case 3:
            status.classList = 'badge badge-soft-warning';
            status.textContent = '검토 요청';
            return;
        case 4:
            status.classList = 'badge badge-soft-danger';
            status.textContent = '오류 수정';
            return;
    }
}