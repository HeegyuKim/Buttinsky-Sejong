package kr.unifox.sejong.spellchecker.mistakes;

import kr.unifox.sejong.spellchecker.Candidate;

public interface MistakeCorrector 
{
	public boolean isCheckStranges();
	public boolean checkMistake(Candidate candidate, Repaired r);
}
