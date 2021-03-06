/******************************************************************************

Copyright (c) 2010, Cormac Flanagan (University of California, Santa Cruz)
                    and Stephen Freund (Williams College) 

All rights reserved.  

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are
met:

 * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.

 * Redistributions in binary form must reproduce the above
      copyright notice, this list of conditions and the following
      disclaimer in the documentation and/or other materials provided
      with the distribution.

 * Neither the names of the University of California, Santa Cruz
      and Williams College nor the names of its contributors may be
      used to endorse or promote products derived from this software
      without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

 ******************************************************************************/

package tools.wdc;

import rr.state.ShadowVar;
import rr.tool.RR;

public class WDCGuardState implements ShadowVar {
	
	//HB
	public volatile CV hbRead;
	public CV hbReadsJoined;
	public volatile CV hbWrite;
	
	//WCP
	public CV wcpRead;
	public CV wcpReadsJoined;
	public CV wcpWrite;
	
	//DC
	public CV dcRead; // for each tid, the clock of the thread when it last read the variable
	public CV dcReadsJoined; // the join of all prior reads
	public CV dcWrite; // the time (the thread's full VC) for the last write
	
	//WDC
	public CV wdcRead;
	public CV wdcReadsJoined;
	public CV wdcWrite;
	
	public int lastWriteTid; // the thread of the last write

	public DynamicSourceLocation[] lastReadEvents; 
	public DynamicSourceLocation lastWriteEvent;

	public WDCGuardState(CV relation) {
		if (WDCTool.HB) {
			hbRead = new CV(WDCTool.INIT_CV_SIZE);
			hbReadsJoined = new CV(WDCTool.INIT_CV_SIZE);
			hbWrite = new CV(WDCTool.INIT_CV_SIZE);
		}
		// WCP maintains both hb and wcp CV so a different constructor is used.
		if (WDCTool.DC) {
			dcRead = new CV(WDCTool.INIT_CV_SIZE);
			dcReadsJoined = new CV(WDCTool.INIT_CV_SIZE);
			dcWrite = new CV(WDCTool.INIT_CV_SIZE);
		}
		if (WDCTool.WDC) {
			wdcRead = new CV(WDCTool.INIT_CV_SIZE);
			wdcReadsJoined = new CV(WDCTool.INIT_CV_SIZE);
			wdcWrite = new CV(WDCTool.INIT_CV_SIZE);
		}
		
		lastWriteTid = -1;
		lastReadEvents = new DynamicSourceLocation[RR.maxTidOption.get()];
	}
	
	public WDCGuardState(CV hb, CV wcp) {
		hbRead = new CV(WDCTool.INIT_CV_SIZE);
		hbReadsJoined = new CV(WDCTool.INIT_CV_SIZE);
		hbWrite = new CV(WDCTool.INIT_CV_SIZE);
		
		wcpRead = new CV(WDCTool.INIT_CV_SIZE);
		wcpReadsJoined = new CV(WDCTool.INIT_CV_SIZE);
		wcpWrite = new CV(WDCTool.INIT_CV_SIZE);
		
		lastWriteTid = -1;
		lastReadEvents = new DynamicSourceLocation[RR.maxTidOption.get()];
	}
	
	public WDCGuardState(CV hb, CV wcp, CV dc) {
		hbRead = new CV(WDCTool.INIT_CV_SIZE);
		hbReadsJoined = new CV(WDCTool.INIT_CV_SIZE);
		hbWrite = new CV(WDCTool.INIT_CV_SIZE);
		
		wcpRead = new CV(WDCTool.INIT_CV_SIZE);
		wcpReadsJoined = new CV(WDCTool.INIT_CV_SIZE);
		wcpWrite = new CV(WDCTool.INIT_CV_SIZE);
		
		if (WDCTool.HB_WCP_DC) {
			dcRead = new CV(WDCTool.INIT_CV_SIZE);
			dcReadsJoined = new CV(WDCTool.INIT_CV_SIZE);
			dcWrite = new CV(WDCTool.INIT_CV_SIZE);
		}
		if (WDCTool.HB_WCP_WDC) {
			wdcRead = new CV(WDCTool.INIT_CV_SIZE);
			wdcReadsJoined = new CV(WDCTool.INIT_CV_SIZE);
			wdcWrite = new CV(WDCTool.INIT_CV_SIZE);
		}
		
		lastWriteTid = -1;
		lastReadEvents = new DynamicSourceLocation[RR.maxTidOption.get()];
	}
	
	public WDCGuardState(CV hb, CV wcp, CV dc, CV wdc) {
		hbRead = new CV(WDCTool.INIT_CV_SIZE);
		hbReadsJoined = new CV(WDCTool.INIT_CV_SIZE);
		hbWrite = new CV(WDCTool.INIT_CV_SIZE);
		
		wcpRead = new CV(WDCTool.INIT_CV_SIZE);
		wcpReadsJoined = new CV(WDCTool.INIT_CV_SIZE);
		wcpWrite = new CV(WDCTool.INIT_CV_SIZE);
		
		dcRead = new CV(WDCTool.INIT_CV_SIZE);
		dcReadsJoined = new CV(WDCTool.INIT_CV_SIZE);
		dcWrite = new CV(WDCTool.INIT_CV_SIZE);
		
		wdcRead = new CV(WDCTool.INIT_CV_SIZE);
		wdcReadsJoined = new CV(WDCTool.INIT_CV_SIZE);
		wdcWrite = new CV(WDCTool.INIT_CV_SIZE);
		
		lastWriteTid = -1;
		lastReadEvents = new DynamicSourceLocation[RR.maxTidOption.get()];
	}

	@Override
	public String toString() {
		return String.format("[HBw=%s HBr=%s WCPw=%s WCPr=%s DCw=%s DCr=%s WDCw=%s WDCr=%s]", hbWrite, hbRead, wcpWrite, wcpRead, dcWrite, dcRead, wdcWrite, wdcRead);
	}
}
