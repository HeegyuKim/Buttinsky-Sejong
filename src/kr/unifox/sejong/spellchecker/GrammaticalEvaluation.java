package kr.unifox.sejong.spellchecker;

public enum GrammaticalEvaluation 
{
	UNEVALUATED,	// 아직 평가되지 않음
	UNKNOWN,		// 분석할 수 없다(아예 모르는 문장)
	STRANGE,		// 이상함(배치가 잘못됨 -> 의그녀: 조사+대명사 ????)
	CORRECT			// 정확함!
}
