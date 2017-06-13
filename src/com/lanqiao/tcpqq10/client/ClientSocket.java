package com.lanqiao.tcpqq10.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
/**
 * @Description: �ͻ���socket��
 * @author forward
 * @date 2017��4��29�� ����6:20:21
 * @version V1.0
 */
public class ClientSocket {
	static Socket socket;
	ChatRoomGUI cf;
	int c;
	public ClientSocket(ChatRoomGUI cf) {
		System.out.println("public ClientSocket(ChatRoomGUI cf) {");
		this.cf = cf;

		// �ͻ������ӷ���������������
		try {
			socket = new Socket(ChatUtil.ADDRESS, ChatUtil.PORT);
			System.out.println("new Thread(new ClientReadWriteThread(socket)).start();");
			// �����߳��ڲ�����ɿͻ��˵Ķ�д����
			new Thread(new ClientReadWriteThread(socket)).start();
			c++;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// �����߳��ڲ�����ɿͻ��˵Ķ�д����
	class ClientReadWriteThread implements Runnable {
		Socket s;
		BufferedReader br;

		public ClientReadWriteThread(Socket socket) {
			System.out.println("socket"+socket);
			this.s = socket;
			try {
				br = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			System.out.println("ClientReadWriteThread run");
			String messege;
			try {
				while ((messege = br.readLine()) != null) {
					System.out.println("��ʶ����" + messege);
					switch (messege) {
					case ChatUtil.FRIEND_LIST:
						messege = br.readLine();
						
						System.out.println(ChatUtil.FRIEND_LIST);
						System.out.println(messege);
						
						String[] info = messege.split(":");

						cf.listModel.removeAllElements();
						cf.box.removeAllItems();
						cf.box.addItem("������");

						for (int i = 0; i < info.length; i++) {
							cf.listModel.addElement(info[i]);
							cf.box.addItem(info[i]);
						}
						break;

					case ChatUtil.OPEN_ROOM:
						messege = br.readLine();
						System.out.println(ChatUtil.OPEN_ROOM);
						System.out.println(messege);
						cf.pubTextArea.append(messege + "\n");
						cf.priTextArea.append(messege + "\n");
						break;

					case ChatUtil.CLOSE_ROOM:
						messege = br.readLine();
						System.out.println(ChatUtil.CLOSE_ROOM);
						System.out.println(messege);
						cf.pubTextArea.append(messege + "\n");
						cf.priTextArea.append(messege + "\n");
						break;

					case ChatUtil.PUBLIC_CHAT:
						messege = br.readLine();
						System.out.println(ChatUtil.PUBLIC_CHAT);
						System.out.println(messege);
						cf.pubTextArea.append(messege + "\n");
						break;

					case ChatUtil.PRIVATE_CHAT:
						messege = br.readLine();
						System.out.println(ChatUtil.PRIVATE_CHAT);
						System.out.println(messege);
						System.out.println(cf.u.getName());
						// ���ݵ�ǰ����Ϣ�Ƿ������ǰ�ͻ��˵���Ϣ����������������˽���������ͻ��˷�����Ϣ��
						if (messege.contains(cf.u.getName())) {
							cf.priTextArea.append(messege + "\n");
						}
						break;
					// case ChatUtil.REFRESH:
					// messege = br.readLine();
					// System.out.println("�û���Ϣ��" + messege);
					// String[] info1 = messege.split(":");
					//
					// cf.listModel.removeAllElements();
					// cf.box.removeAllItems();
					// cf.box.addItem("������");
					// for (int i = 0; i < info1.length; i++) {
					// cf.listModel.addElement(info1[i]);
					// cf.box.addItem(info1[i]);
					// }
					// break;
					default:
						System.out.println("Nothing");
						break;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}