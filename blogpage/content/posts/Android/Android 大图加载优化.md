��ʾ��ͼ����
* �ֿ����
* �ڴ渴�� 
## ʵ��
* ͨ��rect ȷ���洢��ʾ��Χ��
* ͨ��bitmap options ͼƬ��Ϣ
    * options.inJustDecodeBounds=true // ���ÿ��Բ���ȡ���ڴ��л�ȡͼƬ��ߡ�������֮��Ҫ����Ϊfalse
    * options.inMutable=true �����ڴ渴�á�
    * options.inPreferredConfig=bitmap.config.* ����ͼƬ�����ģʽ��
* ͨ�� gestureDetector ��ȡ����
* ͨ��Scroller ����������
* ͨ�� onTouch ʵ�����Ƶ���Ϣ���жϣ����й����ȡ�
* Matrix bitmap �������š�
* ͨ��inputStream ����ͼƬ �������������չ�Ը��ߣ���ΪҪ���ڴ渴�úͷֿ���ء�
    * ��ȡ��Ļ��� options.outWidth/outHeight
    * ��ȡ������룺BitmapRegionDecoder decoder
    * requestLayout() ���»��ơ�
    * onMeasure() ���� ����ʾ�Ŀ�ߡ������ű�����
    * onDraw() ���л���
    * �ڴ渴�� options.inBitmap=mBitmap;
    * 












