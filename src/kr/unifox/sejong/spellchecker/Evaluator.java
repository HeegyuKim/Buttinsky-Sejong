package kr.unifox.sejong.spellchecker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import kr.unifox.sejong.ling.Component;
import kr.unifox.sejong.ling.EoJeol;
import kr.unifox.sejong.ling.HyungTaeSo;
import kr.unifox.sejong.ling.Word;
import kr.unifox.sejong.spellchecker.mistakes.Mistake;

/**
 * 
 * @author 김희규
 *
 * 어절이 옳은 지, 틀린 지를 판별합니다.
 */
public class Evaluator 
{
	Dictionary dic;
	
	public Evaluator(Dictionary dic) 
	{
		this.dic = dic;
	}


	
	// 현재 판별중인거
	CandidateArray array;
	// 결과 stuff~
	EvaluationResult stuff;
	
	/**
	 * 인자 array를 판별해서 GrammaticalEvaluation(ge로 퉁침)을 지정합니다.
	 * ge가 STRANGE 혹은 UNKNOWN이라면 오류 검사를 진행해야 할 것입니다.
	 * @param array
	 * @return 
	 */
	public EvaluationResult evaluate(CandidateArray array)
	{
		/*
		 * 쓰이는 값들 초기화 
		 */
		this.array = array;
		this.stuff = new EvaluationResult();
		this.checkingComps = new Component[array.size()];
		
		
		// 복수의 후보들로 연결된 어절의 경우는
		// 잘 연결되어 있는지 봐야 한다(조사->조사 같은거 안됨)
		if(array.size() > 1)
			checkWellContinued(0, array.get(0), array.get(1));
		
		// 아니면 후보가 하나? 이경우는 그냥 한 개.
		else if(array.size() == 1)
			stuff.corrects.add(array.get(0));
		
		// corrects 에서 조사를 제외한 단어가 하나도 없다면 
		// 이상한 문장이니까 바꿔부려잉
		checkCorrectsHasWordWithoutJosa();
		
		
		//
		// 결과 값 저장합니당. ㅎ
		
		if(stuff.corrects.size() > 0)
			stuff.evaluation = GrammaticalEvaluation.CORRECT;
		
		else if(stuff.stranges.size() > 0)
			stuff.evaluation = GrammaticalEvaluation.STRANGE;
		
		else
			stuff.evaluation = GrammaticalEvaluation.UNKNOWN;
		
		// 옛다 가져라
		return stuff;
	}
	
	// checkWellContinued 메서드에서 재귀호출로 검사할 때
	// 여태까지 검사한 걸 저장해야 하므로
	// 계속해서 검사하던 걸 저장해 놓는 배열임.
	// 으어어 말로하니 어렵네 언어장애 코드보고 알아서 이해하셈
	Component[] checkingComps;
	
	
	/**
	 * 머리 후보집합과 꼬리후보집합이 잘 이어졌는지 확인합니다.
	 * head가 어디(대명사,감탄사) 이고
	 * tail이 에(동사,조사,감탄사) 라면 
	 * 
	 * 대명사-> 조사 는 잘 이어진 거지만
	 * 나머지는 다 문법 씹어먹는 것이므로 생략(감탄사 -> 감탄사?????)
	 * 
	 * CandidateArray의 이후 후보도 재귀호출로 처리해버려엇
	 * 
	 * @param headIndex head가 CandidateArray에서 있는 목차
	 * @param head	검사할 머리부분 후보
	 * @param tail 검사할 꼬리부분 후보
	 */
	private void checkWellContinued(int headIndex, Candidate head, Candidate tail)
	{
		// 마지막 꼬리? -> 이후에 꼬리 더 없음. 재귀호출 그만~
		boolean isLastTail = (array.size() - 2 == headIndex);
		
		// 정확함?
		boolean correct = true;
		
		// 재귀호출로 checkingComps 배열에 잘 담아두고
		// 배열에 담아둔 Component들을 checkSearchingArray() 메서드로 검사하지롱~
		
		
		for(Component headComp : head.compList)
		{
			checkingComps[headIndex] = headComp;
			
			for(Component tailComp : tail.compList)
			{
				checkingComps[headIndex + 1] = tailComp;
							
				// 마지막 꼬리여? 그러면 추가해야제...
				if(isLastTail)
				{
					correct = checkSearchingArray(headIndex + 1);
					
					Candidate candi = new Candidate();
					candi.compList.addAll(Arrays.asList(checkingComps));
					
					if(correct)
						stuff.corrects.add(candi);
					else
					{
						stuff.stranges.add(candi);
						candi.addMistake(new Mistake( 
								rejectedReason,
								Mistake.WRONG_CONTINUED
								));
					}
				}
				// 아니면 계속 찾으라고~
				else
					checkWellContinued(
							headIndex + 1, 
							tail, 
							array.get(headIndex + 2)
							);
				
			}
		}
	}
	
	String rejectedReason;
	
	/**
	 * checkingArray 에 담긴 검색중인 Component가
	 * 잘 이어져 있는지 확인하고 안되면 사유를 rejectedReason에 저장합니다.
	 * @return
	 */
	private boolean checkSearchingArray(int maxLength)
	{
		for(int i = 0; i < maxLength; ++i)
		{
			String headType, tailType;
			Component headComp = checkingComps[i],
					tailComp = checkingComps[i + 1];
			
			
			// 형태소가 아니라 단어일 경우
			// 타입이 아니라 언으로 검사합니당.
			
			if(headComp instanceof Word)
				headType = ((Word)headComp).eon;
			else
				headType = headComp.getTypeName();
			
			if(tailComp instanceof Word)
				tailType = ((Word)tailComp).eon;
			else
				tailType = tailComp.getTypeName();

			// 영 아닌감? 탈락!
			if(!dic.isWellContinued(headType, tailType))
			{
				rejectedReason = String.format("%s(%s) 뒤에 %s(%s)가 이어서 올 수 없습니다.",
						headComp.getSource(), headType,
						tailComp.getSource(), tailType
						);
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 이전에 단순히 구성요소간의 연결성만을 보았다면
	 * 이번에 이 메서드는 전체 어절이 문법적으로 옳은지 봅니다.
	 * -> 조사를 제외한 단어가 존재하는가?
	 * 
	 * Okay -> 그녀의(대명사 + 조사)
	 * Fail -> 으로서(조사 + 조사)
	 */
	private void checkCorrectsHasWordWithoutJosa()
	{
		Iterator<Candidate> it = stuff.corrects.iterator();
		boolean hasWordWithoutJosa = false;
		
		while(it.hasNext())
		{
			Candidate candi = it.next();
			
			for(Component comp : candi.compList)
				if(comp instanceof Word
						&& !comp.getSource().equals(Word.PUMSA_JOSA))
				{
					hasWordWithoutJosa = true;
					break;
				}
			
			if(!hasWordWithoutJosa)
			{
				stuff.stranges.add(candi);
				candi.addMistake(new Mistake( 
						"이 문장은 불완전한 문장입니다. 조사를 제외한 단어가 존재하지 않습니다.",
						Mistake.WRONG_NO_ROOT
						));
				it.remove();
			}
		}
	}
}
