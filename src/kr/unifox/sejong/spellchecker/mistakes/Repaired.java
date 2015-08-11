package kr.unifox.sejong.spellchecker.mistakes;

import kr.unifox.sejong.spellchecker.Candidate;

public class Repaired {

	public Candidate source, repaired;
	public boolean hasMistake;
	public String rejectedReason;
	
	public Repaired() {
		hasMistake = false;
	}

	public Repaired(Candidate source, Candidate repaired, boolean hasMistake
			, String rejectedReason) {
		this.source = source;
		this.repaired = repaired;
		this.hasMistake = hasMistake;
		this.rejectedReason = rejectedReason;
	}
	
}
