document.querySelectorAll('[id^="division"]').forEach(element => {
    element.addEventListener('click', e => {
        window.scrollTo({
            top: 0,
            behavior: "smooth"
        });
        e.preventDefault()
        e.stopPropagation()
        const select = document.getElementById(element.id)
        const projectNo = select.children.namedItem('projectNo').value

        const date = new Date();

        const year = date.getFullYear();
        const month = (date.getMonth() + 1).toString().padStart(2, '0');
        const day = date.getDate().toString().padStart(2, '0');
        const currentDate = `${year}-${month}-${day}`;

        const xhr = new XMLHttpRequest();
        xhr.open('GET','/project/detail/'+projectNo);
        xhr.send();
        xhr.onload = function() {
            if(xhr.status === 200 || xhr.status === 201){
                const projectInfoDTO = JSON.parse(xhr.response)

                document.getElementById('listDiv').style = 'margin: 0px auto;';
                const infoDiv = document.getElementById('infoDiv');
                infoDiv.style = 'margin: 0px auto; display: block;'

                const projectDTO = projectInfoDTO.projectDTO;
                const usersList = projectInfoDTO.usersList;
                const todoList = projectInfoDTO.todoList;

                //Todo 프로젝트 정보
                // ● 제목,설명,날짜
                document.getElementById('title').innerHTML = projectDTO.title;
                document.getElementById('description').innerHTML = projectDTO.description;
                document.getElementById('startDate').innerHTML = projectDTO.startDate;

                let statusClass;
                let text;
                if(projectDTO.status == 0) {
                    statusClass = 'badge rounded-pill bg-warning fs-12'
                    text = '진행중';
                    document.getElementById('notEnd').style = 'display: block;';
                    document.getElementById('end').style = 'display: none;';
                    document.getElementById('dueDate').innerHTML = projectDTO.dueDate;
                } else if(projectDTO.status == 1) {
                    statusClass = 'badge rounded-pill bg-success fs-12'
                    text = '종료';
                    document.getElementById('notEnd').style = 'display: none;';
                    document.getElementById('end').style = 'display: block;';
                    document.getElementById('endDate').innerHTML = projectDTO.endDate;
                }
                document.getElementById('statusInfo').classList = statusClass;
                document.getElementById('statusInfo').textContent = text;

                document.querySelector('#members').innerHTML='';
                usersList.forEach(item => {
                    const $tr = document.createElement("tr");
                    const $temp =
                        `<td class="fw-medium">
                            <img class="rounded-circle header-profile-user" src="${item.profileURL}" alt="Header Avatar" style="width: 50px;height: 50px;">
                            <span style="margin-left: 10px;">${item.nickName}</span>
                            <div class="vr" style="margin: 0px 10px;"></div><span>${item.name}</span>
                            <div class="vr" style="margin: 0px 10px;"></div><span>${item.job}</span>
                            <div class="vr" style="margin: 0px 10px;"></div><span>${item.email}</span>
                         </td>`;
                    $tr.innerHTML=$temp
                    document.querySelector('#members').appendChild($tr);
                })

                document.querySelector('#todolist').innerHTML='';
                let count = 1;

                if(todoList.length == 0){
                    const $tr = document.createElement("tr");
                    const $temp =
                        `<td colspan="3" class="fw-medium" style="text-align: center;">
                                <h5 class="fw-bold" style="margin: 0px;">오늘 종료 예정인 TODO List가 없어요.</h5>
                         </td>`;
                    $tr.innerHTML=$temp
                    document.querySelector('#todolist').appendChild($tr);
                }


                todoList.forEach(item => {
                    if(count == 100) return;

                    if(count == 11) {
                        const $tr = document.createElement("tr");
                        const $temp =
                            `<td colspan="3" class="fw-medium" style="text-align: center;">
                                <button type="button" onclick="location.href='RnR?projectNo=${projectNo}'" class="btn-rounded" style="width: 100px; background-color: #405189;">
                                    <a href="RnR?projectNo=${projectNo}" style="color: white; font-weight: bold;">+ 더보기</a>
                                </button>
                             </td>`;
                        $tr.innerHTML=$temp
                        document.querySelector('#todolist').appendChild($tr);
                        count = 100;
                        return;
                    }

                    if(item.dueDate == currentDate && count <= 10){
                        let title = item.title;
                        let description = item.description;
                        if(title.length > 10)
                            title = title.substring(0,11) + '...'
                        if(description != null && description.length > 20)
                            description = description.substring(0,21) + '...'

                        let $status;
                        if(item.status == 0)
                            $status = `<span class="badge badge-soft-info">진행중</span>`
                        else if(item.status == 1)
                            $status = `<span class="badge badge-soft-success">작업 완료</span>`
                        else if(item.status == 2)
                            $status = `<span class="badge badge-soft-primary">작업 예정</span>`
                        else if(item.status == 3)
                            $status = `<span class="badge badge-soft-warning">검토 요청</span>`
                        else if(item.status == 4)
                            $status = `<span class="badge badge-soft-danger">오류 수정</span>`

                        const $tr = document.createElement("tr");
                        const $temp =
                            `<td class="fw-medium">${title}</td>
                             <td class="fw-medium">${description}</td>
                             <td class="fw-medium">
                                <div id="todo_status">
                                    ${$status}
                                </div>
                             </td>`;
                        $tr.innerHTML=$temp
                        document.querySelector('#todolist').appendChild($tr);
                    }
                    count++;
                })

                document.getElementById('homeBtn').href = 'home?projectNo='+projectNo;

            } else {
                console.error('오류1', xhr.status)
                console.error('오류2', xhr.response)
            }
        }


    });
});