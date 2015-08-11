package kr.unifox.sejong.spellchecker.mistakes;

import kr.unifox.sejong.spellchecker.Candidate;

public interface MistakeCorrector 
{
	public Repaired checkMistake(Candidate candidate);
}
