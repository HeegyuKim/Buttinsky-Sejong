package kr.unifox.sejong.spellchecker;

public class EvaluationResult 
{
	
	public GrammaticalEvaluation evaluation = GrammaticalEvaluation.UNEVALUATED;
	public CandidateArray
					stranges = new CandidateArray(),		// 이상한 것들
					corrects = new CandidateArray();		// 정확한 것들
	
	
	public EvaluationResult() {
		evaluation = GrammaticalEvaluation.UNEVALUATED;
		stranges = new CandidateArray();
		corrects = new CandidateArray();
	}
	
	public EvaluationResult(GrammaticalEvaluation evaluation, 
			CandidateArray stranges,
			CandidateArray corrects) {
		this.evaluation = evaluation;
		this.stranges = stranges;
		this.corrects = corrects;
	}


	public CandidateArray getStranges() {
		return stranges;
	}



	public CandidateArray getCorrects() {
		return corrects;
	}



	public GrammaticalEvaluation getEvaluation()
	{
		return evaluation;
	}


}
