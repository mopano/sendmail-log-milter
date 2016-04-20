
package com.mopano.sendmail.logmilter;

import com.sendmail.milter.IMilterHandler;
import com.sendmail.milter.spi.IMilterHandlerFactory;

public class SimpleHandlerFactory implements IMilterHandlerFactory {

	public IMilterHandler newInstance() {
		return new MilterHandlerImpl();
	}

}
