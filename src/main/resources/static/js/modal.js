// ● [index] - [문의하기]
document.querySelector('#inquiry-send').addEventListener('click',e=>{
    var email = document.getElementById('recipient-name');
    var emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    var content = document.getElementById('message-text')
    var failInfo = document.getElementById('fail-info')

    if (emailPattern.test(email.value)) {
        email.style = '';

        //이메일 유효성 검사 성공
        if(content.value.length > 0){
            //내용 있음
            email.style = 'border: 1px solid green;'
            content.style = 'resize: none; height: 200px; border: 1px solid green;'
            failInfo.innerHTML = ''

            const xhr = new XMLHttpRequest();
            xhr.open('GET','/modal/inquiry/'+email.value+'/'+content.value)
            xhr.send()
            xhr.onload = function() {
                if(xhr.status === 200 || xhr.status === 201){
                    const result = JSON.parse(xhr.response)

                    const $img = document.createElement('img')
                    $img.width = 200;
                    const $div = document.createElement('div')
                    $div.classList = 'mb-3'
                    $div.style = 'padding-top: 20px;'
                    const $h3 = document.createElement('h3')
                    const $h5 = document.createElement('h5')
                    var body = document.getElementById('body')
                    while (body.firstChild)
                        body.removeChild(body.firstChild);

                    body.style = 'text-align: center;'
                    $div.appendChild($h3)
                    $div.appendChild($h5)
                    body.appendChild($img)
                    body.appendChild($div)

                    var footer = document.getElementById('footer')
                    while(footer.firstChild)
                        footer.removeChild(footer.firstChild)
                    const $button = document.createElement('button')
                    $button.type = 'button'
                    $button.classList.add('btn','btn-light')
                    $button.setAttribute('data-bs-dismiss','modal')
                    $button.innerHTML = 'Close'
                    $button.id = 'close'
                    footer.appendChild($button)

                    if(result.result == 1){
                        //메일 발송 성공
                        $img.src = '/assets/images/util/success.png'
                        $h3.innerHTML = '문의 완료!'
                        $h5.innerHTML = '문의사항 검토 후 연락 드리겠습니다.'
                    } else {
                        //메일 발송 실패
                        $img.src = '/assets/images/util/fail.png'
                        $h3.innerHTML = '문의 실패!'
                        $h5.innerHTML = '잠시 후 다시 시도해주세요.'
                    }
                } else {
                    console.error('오류1', xhr.status)
                    console.error('오류2', xhr.response)
                }
            }
        } else {
            //내용 없음
            email.style = 'border: 1px solid green;'
            content.style = 'resize: none; height: 200px; border: 1px solid red;'
            failInfo.innerHTML = '* 문의 내용을 입력해주세요.'

        }
    } else {
        //이메일 유효성 검사 실패
        email.style = 'border: 1px solid red;'
        failInfo.innerHTML = '* 문의자 e-mail을 입력해주세요.'
    }
})

//━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━//
