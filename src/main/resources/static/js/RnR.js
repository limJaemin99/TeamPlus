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
    const xhr = new XMLHttpRequest();
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
    xhr.open('GET','/project/RnR/search/'+projectNo+'/'+page+'/'+condition+'/'+word);
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

    teamTodoList.forEach(item => {
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

        let title = item.title;
        let description = item.description;
        if(title.length > 10)
            title = title.substring(0,11) + '...'
        if(description != null && description.length > 20)
            description = description.substring(0,21) + '...'

        const $temp=
            `<td class="fw-medium">${(searchDTO.page -1)*10+count+1}</td>
             <td class="fw-medium" style="width: 220px;">${title}</td>
             <td class="fw-medium" style="width: 325px;">${description}</td>
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
                        <a href="javascript:comments(${(searchDTO.page -1)*10+count+1},${item.todoNo})" id="comments${(searchDTO.page -1)*10+count+1}" class="page-link">
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
    const xhr = new XMLHttpRequest();
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

//━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━//

//● 댓글 버튼 (누르면 댓글창 생성/삭제)
let $tr;
let current$tr;
function comments(c,todoNo) {
    const id = 'comments'+c

    let count;
    if(c%10 == 0)
        count = 10
    else
        count = c%10

    if ($tr)
        $tr.remove();

    if(current$tr == count) {
        $tr = null
        current$tr = null
        return;
    }

    // ● 댓글창 행 추가
    $tr = document.createElement("tr");
    const $td = document.createElement("td");
        $td.classList = 'fw-medium';
        $td.colSpan = 10;
        // $td.style = 'background-color:lightgray';
    const $div1 = document.createElement("div");
        $div1.classList = 'card';
    const $div2 = document.createElement('div');
        $div2.classList = 'card-body';
    const $div3 = document.createElement('div');
        $div3.classList.add('row', 'g-2');
    const $div4 = document.createElement('div');
        $div4.classList = 'card';

    // ● 댓글창 제목
    const $div5 = document.createElement('div');
        $div5.classList.add('card-header', 'align-items-center', 'd-flex');
        $div5.style = 'background-color:#405189';
    const $h = document.createElement('h4');
        $h.classList.add('card-title', 'mb-0', 'flex-grow-1');
        $h.textContent = 'Comments';
        $h.style = 'color: white; font-weight: bold;';
    const $h2 = document.createElement('h4');
        $h2.classList.add('card-title', 'mb-0', 'flex-grow-1');
        $h2.style = 'color: white; font-weight: bold; text-align: right;';
        $h2.id = 'count';
    // ● 댓글창
    const $div6 = document.createElement('div');
        $div6.classList = 'card-body';
        $div6.style = 'padding-top: 0px;'
    const $div7 = document.createElement('div');
        $div7.setAttribute('data-simplebar',"");
        $div7.style = 'max-height: 300px; overflow: hidden scroll;';
        $div7.classList.add('px-3', 'mx-n3', 'mb-2');
        $div7.id = 'CommentsDiv';

    // ● 댓글 입력창
    const $div8 = document.createElement('div');
        $div8.classList.add('row', 'g-3');
    const $div9 = document.createElement('div');
        $div9.classList = 'col-12';
    const $label = document.createElement('label');
        $label.for = 'exampleFormControlTextarea';
        $label.id = 'commentLabel';
        $label.classList.add('form-label', 'text-body');
        $label.textContent = '댓글 남기기';
    const $textarea = document.createElement('textarea');
        $textarea.classList.add('form-control', 'bg-light', 'border-light');
        $textarea.id = 'exampleFormControlTextarea';
        $textarea.rows = 3;
        $textarea.placeholder = '댓글을 입력해주세요';
        $textarea.style = 'resize: none;';

    // ● 댓글 입력 버튼
    const $div10 = document.createElement('div');
        $div10.classList.add('col-12', 'text-end');
        $div10.style.display = 'flex';
        $div10.style.justifyContent = 'space-between';
    const $h6 = document.createElement('h6');
        $h6.id = 'isSuccess';
    const $i = document.createElement('i');
        $i.classList.add('ri-attachment-line', 'fs-16');
    const $a = document.createElement('a');
        $a.href = `javascript:write(${todoNo},0,0)`;
        $a.id = 'writeBtn';
        $a.classList = 'btn';
        $a.textContent = '작성';
        $a.style = 'background-color:#405189; color: white; font-weight: bold;';

    getComments(todoNo);
    $div10.appendChild($h6);
    $div10.appendChild($a);
    $div9.appendChild($label);
    $div9.appendChild($textarea);
    $div8.appendChild($div9);
    $div8.appendChild($div10);
    $div6.appendChild($div7);
    $div6.appendChild($div8);
    $div5.appendChild($h);
    $div5.appendChild($h2);
    $div4.appendChild($div5);
    $div4.appendChild($div6);
    $div3.appendChild($div4);
    $div2.appendChild($div3);
    $div1.appendChild($div2);
    $td.appendChild($div1);

    $tr.appendChild($td);

    const $target = document.querySelectorAll('tr')[count]; // c번째 tr 요소 선택
    if ($target) {
        $target.insertAdjacentElement('afterend', $tr); // $tr을 선택한 tr 바로 다음에 삽입
    }

    current$tr = count;
}

// ● 댓글 출력
const getComments = function(todoNo) {
    const xhr = new XMLHttpRequest();
    xhr.open('GET','/project/RnR/comments/'+todoNo);
    xhr.send();
    xhr.onload = function() {
        if(xhr.status === 200 || xhr.status === 201){
            const commentsDTO = JSON.parse(xhr.response)

            if(commentsDTO.commentsList.length == 0)
                noComments();
            else {
                makeComments(commentsDTO);
            }
        } else {
            console.error('오류1', xhr.status)
            console.error('오류2', xhr.response)
        }
    }
}

// ● 댓글 없을때
const noComments = function() {
    const date = new Date();

    const year = date.getFullYear();
    const month = (date.getMonth() + 1).toString().padStart(2, '0');
    const day = date.getDate().toString().padStart(2, '0');
    const hours = date.getHours().toString().padStart(2, '0');
    const minutes = date.getMinutes().toString().padStart(2, '0');
    const seconds = date.getSeconds().toString().padStart(2, '0');

    const currentDate = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;

    const temp =
        `<div class="d-flex mb-4" style="margin-top: 16px;">
            <div class="flex-shrink-0">
                <img src="/assets/images/profile/boy1.png" alt="" class="avatar-xs rounded-circle" />
            </div>
            <div class="flex-grow-1 ms-3">
                <h5 class="fs-13">댓글이 없어요!<small class="text-muted ms-2">${currentDate}</small></h5>
                <p class="text-muted">첫번째 댓글의 주인공이 되어보세요!</p>    
                <a href="javascript: void(0);" class="badge text-muted bg-light">
                    <i class="mdi mdi-reply"></i> Reply</a>
            </div>
         </div>
         <div class="d-flex mb-4">
            <div class="flex-shrink-0">
                <img src="/assets/images/profile/girl.png" alt="" class="avatar-xs rounded-circle" />
            </div>
            <div class="flex-grow-1 ms-3">
                <h5 class="fs-13">댓글로 소통해보세요!<small class="text-muted ms-2">${currentDate}</small></h5>
                <p class="text-muted">댓글로 소통하면 더 나은 결과가 나올거에요!</p>    
                <a href="javascript: void(0);" class="badge text-muted bg-light">
                    <i class="mdi mdi-reply"></i> Reply</a>
                <div class="d-flex mt-4">
                    <div class="flex-shrink-0">
                        <img src="/assets/images/profile/mustache.png" alt="" class="avatar-xs rounded-circle" />
                    </div>
                    <div class="flex-grow-1 ms-3">
                        <h5 class="fs-13">댓글에 댓글을 한번 더 작성할 수 있어요!<small class="text-muted ms-2">${currentDate}</small></h5>
                        <p class="text-muted">댓글에 대해 조금 더 대화를 나눠보세요!</p>    
                    </div>
                </div>     
            </div>
         </div>`;

    document.querySelector('#CommentsDiv').innerHTML = temp;
    document.getElementById('count').innerHTML = "댓글 수 : 0";
}

// ● 댓글 있을때
const makeComments = function(commentsDTO) {
    const comments = commentsDTO.commentsList;

    const commentsCount = commentsDTO.commentsList.length + commentsDTO.subCommentsList.length;

    document.querySelector('#CommentsDiv').innerHTML = '';
    let count = 0;
    let count2 = 0;
    let $comment = '';
    comments.forEach(item => {
        $comment +=
            `<div class="d-flex mb-4" style="margin-top: 16px;">
                <div class="flex-shrink-0">
                    <img src="${commentsDTO.memberList[count].profileURL}" alt="" class="avatar-xs rounded-circle" />
                </div>
                <div class="flex-grow-1 ms-3">
                    <h5 class="fs-13">${commentsDTO.memberList[count].nickName}<small class="text-muted ms-2">${item.regDate}</small></h5>
                    <p class="text-muted">${item.content}</p>
                    <a href="javascript: reply(${item.commentNo},'${commentsDTO.memberList[count].nickName}',${item.todoNo});" class="badge text-muted bg-light">
                        <i class="mdi mdi-reply"></i> Reply</a>
                </div>
             </div>`;

        let subNo;
        if (commentsDTO.subCommentsList && commentsDTO.subCommentsList[count2]) {
            subNo = commentsDTO.subCommentsList[count2].commentNo;
        }

        const itemNo = item.commentNo;

        if(subNo == itemNo){
            while(true) {
                $comment +=
                    `<div class="d-flex mt-4" style="padding-left: 50px; border-left: 1px solid lightgrey;">
                    <div class="flex-shrink-0" style="height: 40px;">
                        <img src="${commentsDTO.subMemberList[count2].profileURL}" alt="" class="avatar-xs rounded-circle" />
                    </div>
                    <div class="flex-grow-1 ms-3" style="height: 40px;">
                        <h5 class="fs-13">${commentsDTO.subMemberList[count2].nickName}<small class="text-muted ms-2">${commentsDTO.subCommentsList[count2].regDate}</small></h5>
                        <p class="text-muted">${commentsDTO.subCommentsList[count2].subContent}</p>
                    </div>
                 </div>`;
                count2++;
                let subNo2;
                if (commentsDTO.subCommentsList && commentsDTO.subCommentsList[count2]) {
                    subNo2 = commentsDTO.subCommentsList[count2].commentNo;
                }
                if(subNo2 != itemNo)
                    return;
            }

        }

        count++;
    })
    document.querySelector('#CommentsDiv').innerHTML = $comment;
    document.getElementById('count').innerHTML = '댓글 수 : '+commentsCount;
}

// ● 댓글 작성 + 비동기 재출력
const write = function(todoNo,where,commentNo) {
    const text = document.getElementById('exampleFormControlTextarea').value
    const textarea = document.getElementById('exampleFormControlTextarea')
    const userNo = document.getElementById('userNo').value

    if(text == null || text == '' || text.length == 0){
        alert('댓글 내용을 작성해주세요.')
        return;
    }

    const isSuccess = document.getElementById('isSuccess')

    const xhr = new XMLHttpRequest();
    if(where == 0 && todoNo != 0){ //일반 댓글
        const jsObj = {
            todoNo:todoNo,
            userNo:userNo,
            content:text
        }
        const jsStr = JSON.stringify(jsObj);

        xhr.open('PATCH','/project/RnR/comments/comment');
        xhr.setRequestHeader("Content-Type", "application/json; charset=UTF-8")
        xhr.send(jsStr);
        console.log(jsStr)
        xhr.onload = function() {
            const result = JSON.parse(xhr.response)
            if(xhr.status === 200 || xhr.status === 201){
                if(result.result == 1){    // ● 댓글 작성 성공
                    isSuccess.innerHTML =
                        `<p style="color: green; margin: 0px;"><img src="/assets/images/util/success.png" style="width: 20px; white-space: nowrap; margin-right: 10px;">댓글이 작성되었습니다.</p>`;
                    getComments(todoNo)
                    textarea.value = '';
                    setTimeout(()=>{isSuccess.innerHTML = ''}, 2500)
                } else {    // ● 댓글 작성 실패
                    `<p style="color: red; margin: 0px;"><img src="/assets/images/util/fail.png" style="width: 20px; white-space: nowrap; margin-right: 10px;">댓글 작성을 실패했습니다.</p>`;
                    textarea.value = '';
                    setTimeout(()=>{isSuccess.innerHTML = ''}, 2500)
                }
            } else {
                console.error('오류1', xhr.status)
                console.error('오류2', xhr.response)
            }
        }
    } else if(where == 1 && commentNo != 0) {    //대댓글
        const jsObj = {
            userNo:userNo,
            commentNo:commentNo,
            subContent:text
        }
        const jsStr = JSON.stringify(jsObj);

        xhr.open('PATCH','/project/RnR/comments/subComment');
        xhr.setRequestHeader("Content-Type", "application/json; charset=UTF-8")
        xhr.send(jsStr);
        console.log(jsStr)
        xhr.onload = function() {
            const result = JSON.parse(xhr.response)
            if(xhr.status === 200 || xhr.status === 201){
                if(result.result == 1){    // ● 대댓글 작성 성공
                    isSuccess.innerHTML =
                        `<p style="color: green; margin: 0px;"><img src="/assets/images/util/success.png" style="width: 20px; white-space: nowrap; margin-right: 10px;">댓글이 작성되었습니다.</p>`;
                    getComments(todoNo)
                    textarea.value = '';
                    const $label = document.getElementById('commentLabel');
                    $label.innerHTML = '댓글 남기기';

                    const $a = document.getElementById('writeBtn');
                    $a.href = `javascript:write(${todoNo},0,0)`;
                    setTimeout(()=>{isSuccess.innerHTML = ''}, 2500)
                } else {    // ● 대댓글 작성 실패
                    `<p style="color: red; margin: 0px;"><img src="/assets/images/util/fail.png" style="width: 20px; white-space: nowrap; margin-right: 10px;">댓글 작성을 실패했습니다.</p>`;
                    textarea.value = '';
                    setTimeout(()=>{isSuccess.innerHTML = ''}, 2500)
                }
            } else {
                console.error('오류1', xhr.status)
                console.error('오류2', xhr.response)
            }
        }
    }
}

// ● [대댓글] 버튼 눌렀을때 [댓글]작성 버튼 함수 변경
const reply = function(commentNo,nickname,todoNo) {
    const $button = document.createElement('button');
    $button.type = 'button';
    $button.style = 'margin-left: 10px; background-color:#405189; color: white; font-weight: bold;';
    $button.textContent = '취소';
    $button.id = 'cancel';
    $button.onclick = function () {
        // ● [대댓글] 취소 버튼
        const $label = document.getElementById('commentLabel');
        $label.innerHTML = '댓글 남기기';

        const $a = document.getElementById('writeBtn');
        $a.href = `javascript:write(${todoNo},0,0)`;
    }
    const $label = document.getElementById('commentLabel');
    $label.innerHTML = '댓글 남기기 > reply > ['+nickname+'] 님의 댓글';
    $label.appendChild($button);

    const $a = document.getElementById('writeBtn');
    $a.href = `javascript:write(${todoNo},1,${commentNo})`;
}

//━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━//

// ● 회원 초대
document.getElementById('invite').addEventListener('click',e=>{
   const userNo = document.getElementById('userNo').value;
   const projectNo = document.getElementById('projectNo').value;
   const email = document.getElementById('email').value;

   const newButton = document.createElement('button');
   newButton.type = 'button';
   newButton.classList.add('btn','btn-success');
   newButton.id = 'reInvite';
   newButton.onclick = reInvite;
   newButton.innerHTML = '한명 더 초대하기';

   const xhr = new XMLHttpRequest();
   xhr.open('GET','/project/invite/'+userNo+'/'+projectNo+'/'+email);
   xhr.send();
   xhr.onload = function() {
       if(xhr.status === 200 || xhr.status === 201){
           const result = JSON.parse(xhr.response);

           if(result.result == 1){  //성공
               document.getElementById('image').src = "/assets/images/util/success.png";
               document.getElementById('text1').innerHTML = '초대 코드 전송 완료!';
               document.getElementById('text2').innerHTML = email+' 로 초대코드가 전송되었습니다.';
               document.getElementById('inputDiv').style.display = 'none';
               document.getElementById('invite').style.display = 'none';
               document.getElementById('buttonDiv').appendChild(newButton);
           } else { //실패
               document.getElementById('image').src = "/assets/images/util/fail.png";
               document.getElementById('text1').innerHTML = '초대 코드 전송 실패!';
               document.getElementById('text2').innerHTML = '이메일 확인 후 다시 시도해주세요.';
               document.getElementById('inputDiv').style.display = 'none';
               document.getElementById('invite').style.display = 'none';
               newButton.classList.add('btn','btn-danger');
               newButton.innerHTML = '다시 초대하기';
               document.getElementById('buttonDiv').appendChild(newButton);
           }

       } else {
           console.error('오류1',xhr.status)
           console.error('오류2',xhr.response)
       }
   }

});

const reInvite = function(){
    document.getElementById('image').src = "/assets/images/util/invite.png";
    document.getElementById('text1').innerHTML = '초대할 회원의 이메일을 입력해주세요!';
    document.getElementById('text2').innerHTML = '입력한 회원의 이메일로 초대코드와 비밀번호가 전송됩니다.';
    document.getElementById('email').value = '';
    document.getElementById('inputDiv').style.display = 'block';
    document.getElementById('invite').style.display = 'block';
    document.getElementById('reInvite').remove();
}