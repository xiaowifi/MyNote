### �����е�view �Ļ�������

![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20211201212605.png)

* performTraversals
* MeasureSpec�����ֲ���ģʽ
  ![](https://gitee.com/lalalaxiaowifi/pictures/raw/master/image/20211201214552.png)

### �Զ���viewGroup

> ��Ҫʵ�� 3�����췽����2����εĹ��췽�� Ϊ���봫�롣����Ҫʵ�����з�����

* onMeasure
    * ������л�ȡ���Ĳ���ģʽΪ��ǰ�Լ��Ĳ���ģʽ��MeasureSpec.getMode�����ǻ�ȡ�Ŀ���Ǹ������ṩ�Ŀ�߲ο�ֵ��
    * ����Զ���viewGroup ��Ҫ��ʹ��Ϊ������������Ҫ������viewGroup һ������onMeasure �� ͨ��measureChildWithMargins ������view�Ĳ���ģʽ�� ������view
      �Ͳ�����ʾ���������Ҳ�� �Զ���viewGroup �� onMeasure �п��Ի�ȡ���Լ��Ĳ���ģʽ��ԭ��
    * setMeasuredDimension ���� ���µĲ���ģʽ����ΪҪ������view �Ŀ��
* onLayout
    * ���������Ҫ�Ƕ���view �Ļ���λ�� ���� ���á��ο�ֵ�� onMeasure �м��������
    * ����ӿؼ����аڷ��ǱȽ��������ܵġ�
        * �ӿؼ���ˢ�¡�
        * �����ָ߿�仯��

* onInterceptTouchEvent
  > ��������С���� ���룬���������� ����С�������� �϶�Ϊ����¼���
  > ���������onTouchEvent ����ViewConfiguration ���Ի�ȡ����С�������롣
  > ����˵��������� ������onTouchEvent ִ�С�
* onTouchEvent
  > �����������������
  > Ϊ�������Ի������ڵ�ǰ�����л�ȡ�����������ٶȡ� VelocityTracker.obtain();
* scrollBy ����
  > scrollBy ���������������������ڵ�λ�á��������ǻ����� ��������canvas����view ������ԭ����λ���ϣ�ֻ�ǿ������ǹ����˵ģ����Զ�viewGroup���ԣ���������Ҫ����view �������°ڷš�
  > scrollTo �ǹ�������Ļ������λ�á�
* SparseArray 



































