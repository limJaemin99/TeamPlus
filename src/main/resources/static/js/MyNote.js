//서버의 세션 가져오기
let userSession;
function getSession(){
    const xhr = new XMLHttpRequest();

    xhr.open('GET','/getsession');
    xhr.send();
    xhr.onload = function() {
        if(xhr.status === 200 || xhr.status === 201){
            const session = JSON.parse(xhr.response);
            console.log("▼ 현재 세션 ▼")
            console.log(session)

            userSession = session;
        } else {
            console.error('오류1',xhr.status)
            console.error('오류2',xhr.response)
        }
    }
}
window.addEventListener('load', getSession);
//━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

//검색 버튼
const userSearch = function(){
    const xhr = new XMLHttpRequest();   //비동기 통신 객체 생성
    const userNo = document.getElementById('userNo').value
    const page = document.getElementById('page').value
    let userCondition = document.getElementById('userCondition').value
    let word = document.getElementById('search-word').value;
    if(userCondition != 'all') {
        if (word.trim().length == 0) {
            alert("검색어를 1글자 이상 입력해주세요.\n※ 검색어는 공백이 될 수 없습니다.")
            return;
        }
    } else {
        word = '전체검색'
    }
    xhr.open('GET','/private/tasklist/userSearch/'+page+'/'+userCondition+'/'+word);    //전송 보낼 준비 (url과 method)
    xhr.send();
    xhr.onload=function(){
        if(xhr.status === 200 || xhr.status ===201){
            const userSearchDTO = JSON.parse(xhr.response)

            if(userSearchDTO.myNoteList.length == 0)
                nullList()
            else
                usermakeList(userSearchDTO)
        } else {
            if(xhr.status === 500 && userCondition == 'title') {
                nullList()
            }else if(xhr.status === 500 && userCondition == 'content'){
                nullList()
            }else {
                console.error('오류1', xhr.status)
                console.error('오류2', xhr.response)
            }
        }
    }
}

document.querySelector('#search').addEventListener('click',userSearch);

const usermakeList = function (userSearchDTO){

   document.querySelector('tbody').innerHTML =''

    const myNoteList = userSearchDTO.myNoteList;
    let count = 0;

    myNoteList.forEach(item => {    //배열에서 하나 가져온 member
        const $tr = document.createElement("tr");
        let note = item.noteNo;

        // content 처리
        const passwordText = item.password;
        const asterisks = "*".repeat(passwordText.length);


        const $temp =
            `<td class="fw-medium" name="noteNo" id="noteNo">${(userSearchDTO.page -1)*10+count+1}</td>
			 <td class="fw-medium" name="title">${item.title}</td>
			 <td class="fw-medium" name="content">
            <span class="password-hidden">${asterisks}</span>
             </td>
			 <td class="fw-medium" name="notedate" style="width: 300px;">${item.noteDate}</td>
			 <td class="fw-medium" style="text-align: center; width: 50px;" name="isPrivate">
			    <div class="d-flex">
					<div name="isPrivate">${item.isPrivate}</div>
						<div class="flex-shrink-0 ms-4">
							<ul class="list-inline tasks-list-menu mb-0">
								<li class="list-inline-item"><a href="taskread?noteNo=${note}">
								<i class="ri-eye-fill align-bottom me-2 text-muted"></i></a>
								</li>
								<li class="list-inline-item"><a class="list-inline-item" href="taskmodify?noteNo=${note}">
								<i class="ri-pencil-fill align-bottom me-2 text-muted"></i></a>
								</li>
							</ul>
						</div>
				</div>
			</td>`
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

    const userNo = document.getElementById('userNo').value

    //[페이지 버튼] 처음/뒤로가기
    let back;
    if(userSearchDTO.pageResponseDTO.startPage == 1){    //페이지 시작 번호가 1인 경우
        back = `<li class="page-item disabled">
                    <a href="tasklist?page=1" data-num="1" class="page-link" style="background-color: #EFEFEF"><<</a>
                </li>`
        if(userSearchDTO.page == 1){
            back += `\n<li class="page-item disabled">
                        <a href="tasklist?page=${userSearchDTO.page -1}" data-num="${userSearchDTO.page -1}" class="page-link" style="background-color: #EFEFEF"><</a>
                     </li>`
        } else {
            back += `\n<li class="page-item">
                        <a href="tasklist?page=${userSearchDTO.page -1}" data-num="${userSearchDTO.page -1}" class="page-link"><</a>
                     </li>`
        }
    } else {
        back = `<li class="page-item">
                    <a href="tasklist?page=1" data-num="1" class="page-link"><<</a>
                </li>
                <li class="page-item">
                    <a href="tasklist?page=${userSearchDTO.page -1}" data-num="${userSearchDTO.page -1}" class="page-link"><</a>
                </li>`
    }

    //[페이지 버튼] 페이지 숫자
    let pageNum = '';
    for(let i= userSearchDTO.pageResponseDTO.startPage; i<=userSearchDTO.pageResponseDTO.endPage; i++){
        if(i == userSearchDTO.page){
            pageNum += `<li class="page-item">
                        <a href="tasklist?page=${i}" data-num="${i}" class="page-link active">${i}</a>
                    </li>\n`
        } else {
            pageNum += `<li class="page-item">
                        <a href="tasklist?page=${i}" data-num="${i}" class="page-link">${i}</a>
                    </li>\n`
        }
    }

    //[페이지 버튼] 다음/마지막으로 가기
    let next;
    if(userSearchDTO.pageResponseDTO.endPage == userSearchDTO.pageResponseDTO.totalPage){   //endPage 가 마지막 페이지일 경우
        if(userSearchDTO.page == userSearchDTO.pageResponseDTO.totalPage) {
            next = `<li class="page-item disabled">
                    <a href="tasklist?page=${userSearchDTO.page + 1}" data-num="${userSearchDTO.page + 1}" class="page-link" style="background-color: #EFEFEF">></a>
                </li>`;
        } else {
            next = `<li class="page-item">
                    <a href="tasklist?page=${userSearchDTO.page + 1}" data-num="${userSearchDTO.page + 1}" class="page-link">></a>
                </li>`;
        }
        next += `<li class="page-item disabled">
                    <a href="tasklist?page=${userSearchDTO.pageResponseDTO.totalPage})}" data-num="${userSearchDTO.pageResponseDTO.totalPage}" class="page-link" style="background-color: #EFEFEF">>></a>
                 </li>`;
    } else {
        next = `<li class="page-item">
                    <a href="tasklist?page=${userSearchDTO.page + 1}"/ data-num="${userSearchDTO.page + 1}" class="page-link">></a>
                </li>
                <li class="page-item">
                    <a href="tasklist?page=${userSearchDTO.pageResponseDTO.totalPage}"/ data-num="${userSearchDTO.pageResponseDTO.totalPage}" class="page-link">>></a>
                </li>`;
    }

    const $temp =
        `<li class="page-item" style="margin-right: auto"> ※ 전체 글 개수 : ${ userSearchDTO.pageResponseDTO.totalCount } </li>`;

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

