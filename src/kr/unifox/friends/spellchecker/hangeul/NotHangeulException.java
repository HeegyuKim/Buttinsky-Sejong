package kr.unifox.friends.spellchecker.hangeul;

import kr.unifox.sejong.ling.HangeulException;

public class NotHangeulException extends HangeulException
{
	public NotHangeulException(char notHangeul)
	{
		this(notHangeul + " is not the Hangeul");
	}
	public NotHangeulException()
	{
		// TODO Auto-generated constructor stub
	}

	public NotHangeulException(String arg0)
	{
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public NotHangeulException(Throwable arg0)
	{
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public NotHangeulException(String arg0, Throwable arg1)
	{
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public NotHangeulException(String arg0, Throwable arg1, boolean arg2,
			boolean arg3)
	{
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}

}
