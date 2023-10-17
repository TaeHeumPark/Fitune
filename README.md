# 🏋🏻 Fitune [](https://github.com/dubEng/dubEng/blob/main/ReadME.md#-dubeng-)🏋🏻

**심박수 기반 운동 종목, 강도 추천**

![ic_fitune_favicon.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/cbb7da87-1b39-429d-b9a7-42e9c68c2314/58279549-e726-4a37-8483-b11298ea2b92/ic_fitune_favicon.png)

## [🎯 프로젝트 소개](https://github.com/dubEng/dubEng/blob/main/ReadME.md#-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EC%86%8C%EA%B0%9C)

🏷️ **프로젝트명** : **Fitune (Fit + Fortune)**

## [🎤 서비스 이용해보기](https://github.com/dubEng/dubEng/blob/main/ReadME.md#-%EC%84%9C%EB%B9%84%EC%8A%A4-%EC%9D%B4%EC%9A%A9%ED%95%B4%EB%B3%B4%EA%B8%B0)

🖥 **서비스 apk 파일 주소** : [구글 드라이브 fitune apk](https://drive.google.com/file/d/1o9L3fDo-xrP70JmEsUHLGAQk1LsaAktl/view)

💖 **서비스 소개**

> 나의 심박수에 맞춰 추천해주는 운동 목표 추천, 운동 기록 서비스
진행한 운동량에 따라 세포의 경험치와 레벨이 상승
> 
- **설렁설렁 하던 운동은 이제 그만! ✋**
    
    혼자 운동을 하다 보면 어느 강도로 얼마나 해야 하는지 알기 힘듭니다. 기존의 운동 어플은 단순히 기록용으로만 되어 있습니다.
    
    Fitune 서비스는 사용자의 심박수를 측정해 자신이 원하는 운동 목표(보디 빌딩, 다이어트 등)를 설정하고 알맞은 운동 심박수와 시간을 추천 받음으로써 정확한 개인 맞춤 운동 목표를 설정해줍니다.
    
    아직도 운동을 어떻게 해야 하는지 고민하고 계신가요? 개인형 맞춤 운동 서비스, Fitune과 함께 이제는 성취감 넘치는 운동을 해보세요!
    
- **다양한 콘텐츠 둘러보기 👀**
    
    나와 비슷한 사용자들이 좋아하는 운동 종목을 추천 받을 수 있습니다.
    
    나의 심박수와 연령, 운동 목적를 기반으로 목표 심박수와 시간을 추천 받을 수 있습니다.
    
    운동 기록을 통해 나의 과거 운동 데이터를 확인할 수 있습니다.
    
    운동이 끝날 때마다 평균 심박수와 시간을 바탕으로 캐릭터의 경험치를 올릴 수 있습니다.
    
- **다른 사용자와 대결하기** 🥊
    
    오늘의 운동 데이터를 통해 누가 더 열심히 운동했는지 대결을 할 수 있습니다.
    
    대결 기록을 통해 나의 대결 기록을 확인해보세요.
    
- **운동 추천을 더 정확하게 📈**
    
    Pandas를 사용해서 코사인유사도를 통해 비슷한 유저들의 선호 운동을 추천 받을 수 있습니다.
    
    karvonen 공식을 통해서 사용자에게 맞는 운동 강도를 추천 받을 수 있습니다.
    
    자신에게 맞는 운동을 시작해 원하는 운동 효과를 느껴보세요.
    

👤 **서비스 대상**

- 운동을 하고 싶은데 운동 종목과 운동 강도를 정하기 힘든 분
- 운동 기록을 색다르게 남기고 싶은 분
- 다른 사람들과 경쟁을 통해 나의 동기부여를 높이고 싶은 분
- Fitune에서 운동으로 나의 캐릭터를 키워, 나와 함께 성장하는 모습을 지켜보세요!

☝🏻 **주요 기능**

- 튜토리얼
- 홈
- 운동 진행, 종료, 기록
- 대결하기
- 마이페이지

🗓 **진행 일정**

- 2023.08.28 ~ 2023.10.06 (총 6주)

🔧 기술 스택

| FrontEnd | BackEnd | Big Data | CI/CD |
| --- | --- | --- | --- |
| Android Studio | IntelliJ | Pycharm | Nginx 1.18.0
 |
| Kotlin | Java 17 | Python 3.11.4 | Jenkins |
| Wear OS | Spring Boot 3.0 | Django 3.1.5 | AWS EC2 |
| Bluetooth Low Energy | Gradle | Pandas |  |
| Retrofit | JPA Hibernate |  |  |
| Room | MySQL |  |  |
| Coroutines | Swagger |  |  |
| ViewModel | Lombok |  |  |

👨‍👧‍👧 **팀 소개 - 🙋‍♀️재미원툴🙋‍♂️**

| 유지연 | 김두현 | 배수빈 | 조영헌 | 박태흠 |
| --- | --- | --- | --- | --- |
| BE / Infra | BE | BE | BE | FE |

## [📢 주요 기능 📢](https://github.com/dubEng/dubEng/blob/main/ReadME.md#-%EC%A3%BC%EC%9A%94-%EA%B8%B0%EB%8A%A5-)

---

## 튜토리얼

> 운동 강도 추천을 위해 회원가입 후 안정 심박수, 선호 운동, 활동 심박수를 측정
(저전력 블루투스 통신 - Bluetooth Low Energy)
> 

**1️⃣ 안정심박수 측정**

- 연동된 스마트워치에서 안정시 심박수를 측정 후 설정

**2️⃣ 활동심박수 측정**

- 제자리 뛰기를 진행해 활동시 심박수를 측정

**3️⃣ 세포 이름 생성**

- 모든 튜토리얼 완료 후 나만의 세포 이름 생성하기

![ezgif.com-video-to-gif.gif](https://prod-files-secure.s3.us-west-2.amazonaws.com/cbb7da87-1b39-429d-b9a7-42e9c68c2314/e81bd439-39f4-4755-ad64-9ad4561af23c/ezgif.com-video-to-gif.gif)

## 홈

- 포춘쿠키를 클릭해 오늘의 운동 종목, 운동 강도를 추천 받을 수 있습니다.

**1️⃣ 운동 추천**

- 유저의 개인 맞춤 운동을 추천 받을 수 있습니다.
    - 나이, 안정시 심박수, 활동시 심박수, 운동 목표를 기반으로 karvonen 공식을 사용해 목표에 맞는 운동 강도를 추천 받을 수 있습니다.
- 유저의 키, 나이, 성별, BMI, 안정시 심박수, 활동 심박수, 선호 운동 종목 데이터를 기반으로 Pandas를 사용해서 코사인유사도를 통해 비슷한 유저들의 선호 운동을 추천 받을 수 있습니다.

**2️⃣ 세포 육성**

- 세포의 경험치, 레벨에 따른 이미지를 통해 유저가 동기 부여를 받을 수 있습니다.

![ezgif.com-video-to-gif (2).gif](https://prod-files-secure.s3.us-west-2.amazonaws.com/cbb7da87-1b39-429d-b9a7-42e9c68c2314/70f1ec55-4246-4d1f-9f0c-7202bec5aaba/ezgif.com-video-to-gif_(2).gif)

## 운동 진행, 종료, 기록

**1️⃣ 운동 진행**

- BLE(Bluetooth Low Energy) 통신을 통해 스마트 워치에서 심박수를 1초마다 받아온다.

**2️⃣ 운동 기록 저장**

- 자신이 했던 운동의 난이도, 날씨를 함께 저장할 수 있다.

**3️⃣ 운동 기록**

- 자신이 운동을 했던 기록을 캘린더로 볼 수 있다.
- 특정 운동한 날짜의 UI는 다르게 표시 되고, 운동한 날짜를 클릭하면 하단에 해당 날짜의 운동 정보를 볼 수 있다.

![ezgif.com-video-to-gif (3).gif](https://prod-files-secure.s3.us-west-2.amazonaws.com/cbb7da87-1b39-429d-b9a7-42e9c68c2314/f1eabc92-ac67-47a3-b652-7ed9ae3b0c9e/ezgif.com-video-to-gif_(3).gif)

## 대결하기

**1️⃣ 대결 상대 찾기**

- 오늘의 운동을 끝낸 사람 5명을 랜덤으로 가져온다.

**2️⃣ 대결** 

- 운동 시간, 운동 bpm을 바탕으로 승패를 가른다.

![ezgif.com-video-to-gif (4).gif](https://prod-files-secure.s3.us-west-2.amazonaws.com/cbb7da87-1b39-429d-b9a7-42e9c68c2314/93a25fd2-bd00-4e45-b468-b69c73ee4815/ezgif.com-video-to-gif_(4).gif)

## 마이페이지

**1️⃣ 프로필 정보**

- 프로필 사진과 닉네임, 한 줄 소개, 총 녹음 시간, 총 더빙 작품 수 등 기본 프로필 정보를 확인할 수 있습니다.

**2️⃣ 선호 장르 및 출석 현황**

- 회원가입 시 선택했던 카테고리 장르를 확인할 수 있습니다.
- 캘린더에 출석 현황이 표시됩니다.

![ezgif.com-video-to-gif (5).gif](https://prod-files-secure.s3.us-west-2.amazonaws.com/cbb7da87-1b39-429d-b9a7-42e9c68c2314/c71f6e12-e3c4-4b61-8e77-32e6dbb41f27/ezgif.com-video-to-gif_(5).gif)

## 💥 아키텍처 💥

---

!https://github.com/dubEng/dubEng/raw/main/img/architecture1.png

## [💾 ERD 💾](https://github.com/dubEng/dubEng/blob/main/ReadME.md#-erd-)

---

![특화 ERD.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/cbb7da87-1b39-429d-b9a7-42e9c68c2314/73f74f03-b4fa-4a0b-86eb-ffbba47e9d2b/%ED%8A%B9%ED%99%94_ERD.png)

## 🎨 [목업](https://www.figma.com/file/tmsuUXvulZLYDSoURCfxVr/Untitled?type=design&node-id=0%3A1&mode=design&t=Vo9JuEQf7ndg1i1M-1) 🎨

---

![특화 목업.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/cbb7da87-1b39-429d-b9a7-42e9c68c2314/751f1d51-2799-43a4-a1bd-8c00535f43a0/%ED%8A%B9%ED%99%94_%EB%AA%A9%EC%97%85.png)

## [📜](https://github.com/dubEng/dubEng/blob/main/ReadME.md#-api-%EB%AA%85%EC%84%B8%EC%84%9C-) [API 명세서](https://www.notion.so/8b719696c6834ce8b55ae37207e539fb?pvs=21) 📜

---

![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/cbb7da87-1b39-429d-b9a7-42e9c68c2314/3a1cb3db-bfbe-436d-8264-e06474147a2e/Untitled.png)
