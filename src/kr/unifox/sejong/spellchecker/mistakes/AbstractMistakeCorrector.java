package kr.unifox.sejong.spellchecker.mistakes;

import kr.unifox.sejong.ling.Component;
import kr.unifox.sejong.spellchecker.Candidate;

public abstract class AbstractMistakeCorrector 
implements MistakeCorrector {

	@Override
	public boolean isCheckStranges() {
		return false;
	}

	@Override
	public boolean checkMistake(Candidate candidate, Repaired r) {
		return false;
	}

}
