# 블로그 플랫폼 구축 프로젝트

블로그 플랫폼의 기능들을 back end와 front end로 나누어 개발해 본다

* back end: spring boot restful api
   => backapp
   
* front end: react 
   => cafefront2
  
## back end 프로젝트

#### 블로그 플랫폼의 기능 제공

1. 토큰 기반 인증: 로그인 성공시 토큰 제공. front end에서 매 요청시 토큰을 헤더에 담아 보내면 이를 인증함. 인증되지 않은 요청 처리 불가
2. 블로그 제목, 설명, 카테고리, 대문 사진 등록
3. 관련 글 작성, 목록(페이징), 상세페이지, 수정, 삭제
4. 이웃관리
5. ...

#### 개발 절차

기능정의: 사용자에게 제공할 기능 정의. 기능 제공에 필요한 데이터의 종류와 타입, 결과의 형태도 상의하여 정함

테이블 정의: 기능 구현에 필요한 테이블, 컬럼명, 컬럼 타입, 제약조건, 테이블 사이의 관계를 정의함

구현: 테이블 별로 entity, dto, dao, service, rest controller 구현함

테스팅: post man 툴로 구현한 기능이 원하는 결과를 만들어 내는지 확인

#### 필요 기술

1. 토큰 처리: front end에서 매 요청시 토큰을 헤더에 담아 보냄. 그래서 요청 헤더에서 토큰을 꺼내려면 헤더 이름을 양쪽에서 맞춰야함

   TokenProvider 클래스
   
   public String resolveToken(HttpServletRequest request) {

      return request.getHeader("auth_token"); // front end에서 헤더에 auth_token이름으로 토큰 보내야함

   }

3. 파일업로드
  - application.properties: spring.servlet.multipart.location=C:/img/      [파일로 이동](github.com/kingnuna/backapp/blob/master/src/main/resources/application.properties)
  - controller:

    
    @Value("${spring.servlet.multipart.location}")

    
	 private String path;

    public Map upload(Dto dto){

      String fname = dto.getF().getOriginalFilename(); //upload된 파일의 원본 파일명
    
      File f = new File(path + fname); //upload된 파일을 저장할 새 파일 생성
    
      dto.getF().transferTo(f); //MultipartFile 클래스의 transferTo() 메서드로 업로드된 파일을 서버에 저장

    }
    
   
5. 이미지 전송



