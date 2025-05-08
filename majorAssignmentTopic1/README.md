# COMP1010-GroupK-

//Structure(지워도됨 나중에에)
majorAssignmentRpgGame.zip
└── majorAssignmentTopic1
    ├── Client.java
    ├── Battle.java
    ├── Team.java
    ├── Character.java
    ├── Dice.java
    ├── Action.java
    ├── StatusEffect.java (optional)
    ├── README.txt
    ├── UML Diagram.png
    └── lib (optional, e.g. JUnit)


//*Problem Statement




// *UML Diagram



// *Design  Process(클래스 설계 + 주요 구성요소)




// *Task Allocation




// *How to Run




// *Method Analysis
(두개 세개 가능한 메소드를 비교해서 ,왜 더 나은 선택인지 설명 -> ex)메모리 효율 etc.


--------------------------------------------------------------------------------------------------------------------
// 최종검토 사항
// 1-파일이름 , 팀원기여도 분배 ,how to run 미기재시 ,감점


//Component B(10%)
 1- 주석처리 : 메소드마다 - 이 메소드의 기능,이유,왜 등 핵심내용위주로만만
(예시) 

boolean allUnique(int[] data) {
  /*
  the logic of my design and implementation is to
  compare each item in the array with every OTHER
  item (not itself), and returning false as soon
  as two items at different indices are the same
  in their value
  */

  if(data == null) {    //to avoid NullPointerException
    return false;
    }
}


// 2- 들여쓰기 
좋은예시

Example of well-indented code:

int mystery(int[] a) {
  int count = 0;
//one blank line to separate logical sections, if needed

  for(int i=0; i < a.length; i++) {
    for(int k=i+1; k < a.length; k++) {
      if(a[i] == a[k]) {
          count++;
        }
    }
}
//again, at most one blank line

return false;
}



// 3. 변수이름  -> camelCased , final 사용시 모두 다 CAPITAL  ** 의미있는 변수이름으로만 사용할것**

//4. Delegation  (기능 분리)
같은 로직 반복 → 별도 메소드로 만들기


