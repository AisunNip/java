package th.co.truecorp.crmdev.asset;

import java.net.UnknownHostException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import th.co.truecorp.crmdev.asset.bean.Constants;
import th.co.truecorp.crmdev.util.logging.LogProductName;
import th.co.truecorp.crmdev.util.logging.LogSystem;
import th.co.truecorp.crmdev.util.logging.PatternLogger;

public class AssetLogger extends PatternLogger {

	public AssetLogger(LogProductName productName, LogSystem sourceSystem, LogSystem targetSystem)
		throws UnknownHostException {
		super(Constants.APPLICATION_NAME, productName, sourceSystem, targetSystem);
	}

	@Override
	public Logger initialLogger() {
		return LogManager.getLogger(AssetLogger.class);
	}
}