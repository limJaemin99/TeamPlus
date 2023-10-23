//검색 버튼
const search = function(){
    const xhr = new XMLHttpRequest();   //비동기 통신 객체 생성
    var projectNo = document.getElementById('projectNo').value
    console.log(projectNo)
    let condition = document.getElementById('condition').value
    console.log('현재 선택된 검색 조건 : '+condition)
    let word = document.getElementById('search-word').value;
    console.log(word)

    xhr.open('GET','/project/RnR/search/'+projectNo+'/'+condition+'/'+word);    //전송 보낼 준비 (url과 method)
    xhr.send();
    xhr.onload=function(){
        if(xhr.status === 200 || xhr.status ===201){
            const todoList = JSON.parse(xhr.response)
            makeList(todoList)  //makeList 함수는 아래에 있음
        } else {
            console.error('오류1',xhr.status)
            console.error('오류2',xhr.response)
        }
    }
}
document.querySelector('#search').addEventListener('click',search);

//응답받은 Todo 목록을 태그로 출력하는 함수입니다.
const makeList = function (todoList){         //list 는 dto타입과 동일한 자바스크립트 객체 배열입니다.
    // console.log(list);

    document.querySelector('tbody').innerHTML =''         //테이블의 기존 내용은 clear
    //  전달받은 list 를 각 항목을 table td 태그로 출력
    let count = 1;
    todoList.forEach(item => {    //배열에서 하나 가져온 member
        const $tr = document.createElement("tr");
        const $temp=
            `<td class="fw-medium">count</td>
             <td class="fw-medium">${item.title}</td>
             <td class="fw-medium">${item.description}</td>
             <td class="fw-medium">${item.todoDate}</td>
             <td class="fw-medium">${item.dueDate}</td>
             <td class="fw-medium">${item.endDate}</td>
             <td class="fw-medium">프로필 사진</td>` ;
        $tr.innerHTML=$temp;
        document.querySelector('tbody').appendChild($tr);
        count++;
    });
}