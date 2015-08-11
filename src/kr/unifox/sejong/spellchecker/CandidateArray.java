package kr.unifox.sejong.spellchecker;

import java.util.ArrayList;
import java.util.Collection;

public class CandidateArray extends ArrayList<Candidate> {

	
	public CandidateArray() {
		super();
	}

	public CandidateArray(Collection<? extends Candidate> c) {
		super(c);
	}

	public CandidateArray(int initialCapacity) {
		super(initialCapacity);
	}
	
	
}
