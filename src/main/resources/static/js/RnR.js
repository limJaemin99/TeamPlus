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
    const projectNo = document.getElementById('projectNo').value
    const page = document.getElementById('page').value
    let condition = document.getElementById('condition').value
    let word = document.getElementById('search-word').value;
    if(condition != 'all' && condition != 'imminent') {
        if (word.trim().length == 0) {
            alert("검색어를 1글자 이상 입력해주세요.\n※ 검색어는 공백이 될 수 없습니다.")
            return;
        }
    } else {
        word = '전체검색'
    }
    xhr.open('GET','/project/RnR/search/'+projectNo+'/'+page+'/'+condition+'/'+word);    //전송 보낼 준비 (url과 method)
    xhr.send();
    xhr.onload=function(){
        if(xhr.status === 200 || xhr.status ===201){
            const searchDTO = JSON.parse(xhr.response)

            if(searchDTO.teamTodoList.length == 0)
                nullList()
            else
                makeList(searchDTO)
        } else {
            if(xhr.status === 500 && condition == 'incharge')
                nullList()
            else {
                console.error('오류1', xhr.status)
                console.error('오류2', xhr.response)
            }
        }
    }
}

document.querySelector('#search').addEventListener('click',search);

//응답받은 To-do 목록을 태그로 출력하는 함수
const makeList = function (searchDTO){
    document.querySelector('tbody').innerHTML =''

    const teamTodoList = searchDTO.teamTodoList;
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
        let todo = item.todoNo;
        if(item.userNo == currentSession.userNo)
            statusButton = "<div class='avatar-group-item'>\n" +
                `    <a href=\'javascript:changeStatus(1,${count},${todo})\' class='d-inline-block'>\n` +
                "        <img src='/assets/images/status/green.png' class='rounded-circle avatar-xxs'>\n" +
                "    </a>\n" +
                "</div>\n" +
                "<div class='avatar-group-item'>\n" +
                `    <a href='javascript:changeStatus(4,${count},${item.todoNo})' class='d-inline-block'>\n` +
                "        <img src='/assets/images/status/red.png' class='rounded-circle avatar-xxs'>\n" +
                "    </a>\n" +
                "</div>\n" +
                "<div class='avatar-group-item'>\n" +
                `    <a href='javascript:changeStatus(2,${count},${item.todoNo})' class='d-inline-block'>\n` +
                "        <img src='/assets/images/status/indigo.png' class='rounded-circle avatar-xxs'>\n" +
                "    </a>\n" +
                "</div>\n" +
                "<div class='avatar-group-item'>\n" +
                `    <a href='javascript:changeStatus(0,${count},${item.todoNo})' class='d-inline-block'>\n` +
                "        <img src='/assets/images/status/blue.png' class='rounded-circle avatar-xxs'>\n" +
                "    </a>\n" +
                "</div>\n" +
                "<div class='avatar-group-item'>\n" +
                `    <a href='javascript:changeStatus(3,${count},${item.todoNo})' class='d-inline-block'>\n` +
                "        <img src='/assets/images/status/yellow.png' class='rounded-circle avatar-xxs'>\n" +
                "    </a>\n" +
                "</div>\n" +
                `<div id='success${count}' style='margin-left: 10px;'></div>`;
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

        let $endDate;
        if(item.endDate == null)
            $endDate = '';
        else
            $endDate = item.endDate

        const $temp=
            `<td class="fw-medium">${(searchDTO.page -1)*10+count+1}</td>
             <td class="fw-medium">${item.title}</td>
             <td class="fw-medium">${item.description}</td>
             <td class="fw-medium">${item.todoDate}</td>
             <td class="fw-medium">${item.dueDate}</td>
             <td class="fw-medium">${$endDate}</td>
             <td class="fw-medium">
                <img class="rounded-circle header-profile-user"
                    src="${searchDTO.usersList[count].profileURL}" alt="Header Avatar">
                ${searchDTO.usersList[count].nickName}
             </td>
             <td class="fw-medium">${status}</td>
             <td class="fw-medium">
                <div class="avatar-group flex-nowrap" style="width: 50px;">
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

    //● 페이지네이션 추가 부분

    //기존 페이지 번호 삭제
    const element = document.getElementById('main');
    if (element)
        element.parentNode.removeChild(element);

    const $ul = document.createElement('div')
    $ul.classList.add('pagination', 'pagination-wrap', 'pagination-sm', 'mb-0');
    $ul.id = 'main';

    const projectNo = document.getElementById('projectNo').value

    //[페이지 버튼] 처음/뒤로가기
    let back;
    if(searchDTO.pageResponseDTO.startPage == 1){    //페이지 시작 번호가 1인 경우
        back = `<li class="page-item disabled">
                    <a href="RnR?projectNo=${projectNo}&page=1" data-num="1" class="page-link" style="background-color: #EFEFEF"><<</a>
                </li>`
        if(searchDTO.page == 1){
            back += `\n<li class="page-item disabled">
                        <a href="RnR?projectNo=${projectNo}&page=${searchDTO.page -1}" data-num="${searchDTO.page -1}" class="page-link" style="background-color: #EFEFEF"><</a>
                     </li>`
        } else {
            back += `\n<li class="page-item">
                        <a href="RnR?projectNo=${projectNo}&page=${searchDTO.page -1}" data-num="${searchDTO.page -1}" class="page-link"><</a>
                     </li>`
        }
    } else {
        back = `<li class="page-item">
                    <a href="RnR?projectNo=${projectNo}&page=1" data-num="1" class="page-link"><<</a>
                </li>
                <li class="page-item">
                    <a href="RnR?projectNo=${projectNo}&page=${searchDTO.page -1}" data-num="${searchDTO.page -1}" class="page-link"><</a>
                </li>`
    }

    //[페이지 버튼] 페이지 숫자
    let pageNum = '';
    for(let i= searchDTO.pageResponseDTO.startPage; i<=searchDTO.pageResponseDTO.endPage; i++){
        if(i == searchDTO.page){
            pageNum += `<li class="page-item">
                        <a href="RnR?projectNo=${projectNo}&page=${i}" data-num="${i}" class="page-link active">${i}</a>
                    </li>\n`
        } else {
            pageNum += `<li class="page-item">
                        <a href="RnR?projectNo=${projectNo}&page=${i}" data-num="${i}" class="page-link">${i}</a>
                    </li>\n`
        }
    }

    //[페이지 버튼] 다음/마지막으로 가기
    let next;
    if(searchDTO.pageResponseDTO.endPage == searchDTO.pageResponseDTO.totalPage){   //endPage 가 마지막 페이지일 경우
        if(searchDTO.page == searchDTO.pageResponseDTO.totalPage) {
            next = `<li class="page-item disabled">
                    <a href="RnR?projectNo=${projectNo}&page=${searchDTO.page + 1}" data-num="${searchDTO.page + 1}" class="page-link" style="background-color: #EFEFEF">></a>
                </li>`;
        } else {
            next = `<li class="page-item">
                    <a href="RnR?projectNo=${projectNo}&page=${searchDTO.page + 1}" data-num="${searchDTO.page + 1}" class="page-link">></a>
                </li>`;
        }
        next += `<li class="page-item disabled">
                    <a href="RnR?projectNo=${projectNo}&page=${searchDTO.pageResponseDTO.totalPage})}" data-num="${searchDTO.pageResponseDTO.totalPage}" class="page-link" style="background-color: #EFEFEF">>></a>
                 </li>`;
    } else {
        next = `<li class="page-item">
                    <a /*href="RnR?projectNo=${projectNo}&page=${searchDTO.page + 1}"*/ data-num="${searchDTO.page + 1}" class="page-link">></a>
                </li>
                <li class="page-item">
                    <a /*href="RnR?projectNo=${projectNo}&page=${searchDTO.pageResponseDTO.totalPage}"*/ data-num="${searchDTO.pageResponseDTO.totalPage}" class="page-link">>></a>
                </li>`;
    }

    const $temp =
        `<li class="page-item" style="margin-right: auto"> ※ 전체 글 개수 : ${ searchDTO.pageResponseDTO.totalCount } </li>`;

    $ul.innerHTML = $temp + back + pageNum + next
    document.querySelector('#mainDiv').appendChild($ul);
}

//검색 결과 출력할 내용이 없을 경우
const nullList = function() {
    document.querySelector('tbody').innerHTML =''
    const $tr = document.createElement("tr");
    const $temp =
        `<td colspan="10" class="fw-medium" style="text-align: center;">
            <img src="/assets/images/util/searchFail.png" width="100px;" style="margin: 30px;">
            <h1>검색 결과가 없습니다</h1>
         </td>`
    $tr.innerHTML=$temp;
    document.querySelector('tbody').appendChild($tr);
}

//status 변경하는 함수
function changeStatus(s,c,todoNo){
    var status = document.getElementById('currentStatus'+c)

    function update() {
        let success = 'success'+c
        const successDiv = document.getElementById(success)
        const img = document.createElement('img')
        img.src = '/assets/images/util/success.gif'
        img.classList = 'rounded-circle avatar-xxs gu-unselectable'

        let isExist = (successDiv.innerHTML.trim().length == 0)

        const jsObj = {
            todoNo: todoNo,
            status: s
        }

        const jsStr = JSON.stringify(jsObj)

        const xhr = new XMLHttpRequest()
        xhr.open('PATCH', '/project/RnR/update')
        xhr.setRequestHeader("Content-Type", "application/json; charset=UTF-8")
        xhr.send(jsStr)
        xhr.onload = function () {
            const result = JSON.parse(xhr.response)
            if (xhr.status === 200 || xhr.status === 201) {
                if(isExist) {
                    if (result.count == 1) {
                        successDiv.appendChild(img)
                        setTimeout(()=>{document.getElementById(success).innerHTML = ''}, 1500)
                    } else {
                        img.src = '/assets/images/util/fail.gif'
                        successDiv.appendChild(img)
                        setTimeout(()=>{document.getElementById(success).innerHTML = ''}, 1500)
                    }
                }
            }
        }
    }

    switch (s){
        case 0 :
            status.classList = 'badge badge-soft-info';
            status.textContent = '진행중';
            update();
            return;
        case 1:
            status.classList = 'badge badge-soft-success';
            status.textContent = '작업 완료';
            update();
            return;
        case 2:
            status.classList = 'badge badge-soft-primary';
            status.textContent = '작업 예정';
            update();
            return;
        case 3:
            status.classList = 'badge badge-soft-warning';
            status.textContent = '검토 요청';
            update();
            return;
        case 4:
            status.classList = 'badge badge-soft-danger';
            status.textContent = '오류 수정';
            update();
            return;
    }
}

//━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━//

//Status 기준 검색
const statusSearch = function() {
    const xhr = new XMLHttpRequest();
    var projectNo = document.getElementById('projectNo').value
    const page = document.getElementById('page').value
    let status = document.getElementById('statusSelect').value

    xhr.open('GET','/project/RnR/search/'+projectNo+'/'+page+'/'+status)
    xhr.send()
    xhr.onload = function() {
        if(xhr.status === 200 || xhr.status === 201){
            const searchDTO = JSON.parse(xhr.response)

            if(searchDTO.teamTodoList.length == 0)
                nullList()
            else
                makeList(searchDTO)
        } else {
            if(xhr.status === 500)
                nullList()
            else {
                console.error('오류1', xhr.status)
                console.error('오류2', xhr.response)
            }
        }
    }
}

document.querySelector('#statusSearch').addEventListener('click',statusSearch)

//━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━//

//[페이지 이동] - 첫번째 페이지
const movePage = function(pageNum){
    const xhr = new XMLHttpRequest();   //비동기 통신 객체 생성
    const projectNo = document.getElementById('projectNo').value
    let condition = document.getElementById('condition').value
    let word = document.getElementById('search-word').value;
    if(condition != 'all' && condition != 'imminent') {
        if (word.trim().length == 0) {
            alert("검색어를 1글자 이상 입력해주세요.\n※ 검색어는 공백이 될 수 없습니다.")
            return;
        }
    } else {
        word = '전체검색'
    }
    xhr.open('GET','/project/RnR/search/'+projectNo+'/'+pageNum+'/'+condition+'/'+word);    //전송 보낼 준비 (url과 method)
    xhr.send();
    xhr.onload=function(){
        if(xhr.status === 200 || xhr.status ===201){
            const searchDTO = JSON.parse(xhr.response)

            if(searchDTO.teamTodoList.length == 0)
                nullList()
            else
                makeList(searchDTO)
        } else {
            if(xhr.status === 500 && condition == 'incharge')
                nullList()
            else {
                console.error('오류1', xhr.status)
                console.error('오류2', xhr.response)
            }
        }
    }
}

document.querySelector('#mainDiv').addEventListener('click', e => {
    e.preventDefault();
    e.stopPropagation();
    const target = e.target;

    if (target.closest('#main')) {
        if (target.tagName === 'A') {
            const pageNum = target.getAttribute('data-num');
            console.log('요청한 페이지 번호 : ' + pageNum);
            movePage(pageNum);
        }
    }
});
