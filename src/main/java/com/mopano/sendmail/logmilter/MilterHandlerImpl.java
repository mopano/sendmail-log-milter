/*
 * Copyright (c) 2016 Mopano Ltd.
 */
package com.mopano.sendmail.logmilter;

import com.sendmail.milter.AMilterHandlerAdapter;
import com.sendmail.milter.IMilterStatus;
import com.sendmail.milter.MilterConstants;
import java.nio.charset.Charset;

import java.util.Arrays;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MilterHandlerImpl extends AMilterHandlerAdapter {

	private static final Logger LOG = LoggerFactory.getLogger(MilterHandlerImpl.class);

	private static final Charset UTF8 = Charset.forName("UTF-8");

	/**
	 * Because the filter only implements {@link #envfrom} and {@link #header}, corresponding to
	 * {@link MilterConstants#SMFIP_NOMAIL}, {@link MilterConstants#SMFIP_NORCPT} and
	 * {@link MilterConstants#SMFIP_NOHDRS}, we remove those flags from {@link MilterConstants.SMFI_V2_PROT} to tell the
	 * MTA to send us those packets.
	 */
	private static final int ACTIONS = MilterConstants.SMFI_V2_PROT
			^ (MilterConstants.SMFIP_NOMAIL | MilterConstants.SMFIP_NORCPT | MilterConstants.SMFIP_NOHDRS);

	@Override
	public int getActionFlags() {
		return ACTIONS;
	}

	@Override
	public IMilterStatus envfrom(byte[][] argv, Properties properties) {
		String[] arguments = new String[argv.length];
		for (int i = 0; i < argv.length; i++) {
			arguments[i] = new String(argv[i], UTF8);
		}
		LOG.info("Incoming MAIL FROM: " + Arrays.toString(arguments));
		return DEFAULT_CONTINUE;
	}

	@Override
	public IMilterStatus envrcpt(byte[][] argv, Properties properties) {
		String[] arguments = new String[argv.length];
		for (int i = 0; i < argv.length; i++) {
			arguments[i] = new String(argv[i], UTF8);
		}
		LOG.info("Incoming RCPT TO: " + Arrays.toString(arguments));
		return DEFAULT_CONTINUE;
	}

	@Override
	public IMilterStatus header(byte[] name, byte[] value) {
		LOG.info("Header: \"" + new String(name, UTF8) + "\" value: \"" + new String(value, UTF8) + '"');
		return DEFAULT_CONTINUE;
	}

}
