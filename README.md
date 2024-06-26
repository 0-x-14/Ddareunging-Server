## 🚲 따릉잉 서버 리포지토리 🚲


### 📥 브랜치 종류

* main - 최종본, 배포 전까지 수정 X
* develop - 배포 전 최종본
* main, develop 브랜치가 아닌 **별도의 브랜치에서 개발**하기❗ (하단 브랜치 관리 방법 참고)
<br><br><br>

### 📥 커밋 방법

- commit 형식
    - [Feat]: 새로운 기능 추가
    - [Fix]: 버그 수정
    - [Docs]: 문서 수정
    - [Style]: 코드 포맷팅, 세미콜론 누락, 코드 변경이 없는 경우
    - [Refactor]: 코드 리펙토링
    - [Test]: 테스트 코드, 리펙토링 테스트 코드 추가
    - [Chore]: 빌드 업무 수정, 패키지 매니저 수정
    
    🖥️ **`git add 자신이 수정한 파일명`**
    
    🖥️ **`git commit -m “[Feat] ㅇㅇ기능 추가”`**
<br><br><br>
### 📥 브랜치 관리 방법
* 새로운 이슈 발행
  * 이슈 이름은 커밋과 동일한 형식
  * 🖥️ **`[Feat] 카카오 로그인 API 구현`**
    
* 이슈 번호에 맞춰 새로운 브랜치 생성
  * 이때, source는 develop으로 설정할 것. **_main 브랜치 아님❗_**
  * `feat/1`과 같이 생성
    
* **생성한 브랜치에서 개발**
  
* 개발 완료 후 develop 브랜치에 pull request
  * PR 이름은 커밋과 동일한 형식
  * PR : **`base: [develop] <- compare: [개발한 브랜치]`** 로 설정. **_main 브랜치 아님❗_**
  * 🖥️ **`[Feat] 카카오 로그인 API 구현`**
    
* conflict 발생하지 않으면 merge 진행
  
* merge 완료 후 로컬에서 develop 브랜치 pull해와서 **정상적으로 실행되는지 확인❗**

