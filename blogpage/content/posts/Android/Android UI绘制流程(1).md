## app ��������
* activityThread��handler �� main  ������ڡ�
* һ��Ӧ�ã�����һ�����̡�
* applicationThread activityTread ͨ������ȡAMS
* instrumenttation ��������activity���� Application�Ȱ����������ڷ����ĵ��á�
![app ��������ͼ](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/App%E5%90%AF%E5%8A%A8%E6%B5%81%E7%A8%8B.jpg)

* activityStackSupervisor activity�Ķ�ջ��
* activityRecord activity ��Ϣ�洢�ࡣ
* clientTransaction activity ��������
* launchActivityItem  

## activity ��������

## View ��������
![activity���� xml ����ͼ](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/Activity%E7%9A%84XML%E8%A7%A3%E6%9E%90%E5%B8%83%E5%B1%80.jpg)
![UI�����������ͼ](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/UI%E7%9A%84%E5%85%B7%E4%BD%93%E7%BB%98%E5%88%B6%E6%B5%81%E7%A8%8B.jpg)
* phoneWindow ��ʾ����Ψһ��ʵ���ࡣ
    * DecorView ���ڵĶ�����ͼ��
* AppcompatDelegateImpl  appCompatActivity �Ĵ���
* setContentView ��Ҫ�����ǽ���xml �ļ���������ӵ����ڵĸ�view�ϡ�
    * ͬʱ����viewRootImpl ���й���
* windowMangerImpl ���ڹ�������ʵ���ࡣ����View����ӵ������ϡ�
* windowMangerGlobal 
* requestLayout ���²�������view��
* measure()  ������
* onDraw() ���ơ�
* ����Ĳ��� ���ƶ�����Resume ��ִ�еġ�
* ˢ���ʺ�֡���� ��Ҫ����Ļ��CPU/GPU
    * ��Ļ ��ʾ���ݡ�60HZ(1����60��ˢ��=ˢ����)
    * CPU/GUP ������ʾ���ݡ�120FPS(֡����) 120FPS 1���120����ʾͼ��
* project buffer Ϊ�˽�� ˢ���ʺ�֡���ʲ�ͬ�������⡣
    * VSync ��ֱͬ�� 16ms = 60FPS  ��Ҫ��ȥ�ײ�ȥ����һ֡���������ʱ�䡣
    * choreographer ��Ҫ���ʵ�� ��ֱͬ�������ڿ���ͬ����Ϣ������ƶ�����������
    * framebuffer ֡���棬��Linux ϵͳ���ơ�
    * ���յ��źŲŽ��м��㡣
    * choreographer ά����һ��handler 
    * mFrameScheduled ͬһֻ֡�����һ�Ρ�
    * SurfaceFlinger  �������̣���init.rc ������������ams ������ࡣ
* surface ʵ��-��װ������ص����ݡ�    
## 





