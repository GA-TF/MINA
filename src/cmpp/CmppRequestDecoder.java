package cmpp;


import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cmpp.pdu.CmppPDU;
import cmpp.pdu.CmppPDUParser;
import cmpp.sms.ByteBuffer;


/**
 * TODO: Document me !
 * 
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class CmppRequestDecoder extends CumulativeProtocolDecoder {
	private static final Logger logger = LoggerFactory
			.getLogger(CmppRequestDecoder.class);

	@Override
	protected boolean doDecode(final IoSession session, IoBuffer in,
			ProtocolDecoderOutput out) throws Exception {

		// �������¼��������
		// 1. һ��ip����ֻ����һ��������Ϣ
		// 2. һ��ip���а���һ��������Ϣ����һ����Ϣ��һ����
		// 3. һ��ip���а���һ����Ϣ��һ����
		// 4. һ��ip���а�������������������Ϣ����ࣨѭ�������ڸ����decode�У�

		if (in.remaining() > 4) {
			logger.info("resv msg " + in.toString());
			in.mark();
			int length = in.getInt();			
			logger.info("length=" + length + ",in.limit=" + in.limit()+",in.remaining="+in.remaining());
			if (length > (in.remaining()+4))
			{
				in.rewind();
				return false;
			}

			byte[] bytedata = new byte[length-4];
			in.get(bytedata);	
			ByteBuffer buffer = new ByteBuffer();
			buffer.appendInt(length);
			buffer.appendBytes(bytedata);
			CmppPDU pdu = CmppPDUParser.createPDUFromBuffer(buffer);
			if (pdu == null) return false;
			logger.info(pdu.dump());
			out.write(pdu);
			return true;

		}

		return false;
	}
}
