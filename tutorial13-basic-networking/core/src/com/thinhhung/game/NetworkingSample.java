package com.thinhhung.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

public class NetworkingSample implements ApplicationListener {
	OrthographicCamera camera;
	SpriteBatch batch;
	Skin skin;
	Stage stage;
	Label labelDetails;
	Label labelMessage;
	TextButton button;
	TextArea textIPAddress;
	TextArea textMessage;

	// Tỉ lệ 16:9
	float VIRTUAL_SCREEN_HEIGHT = 960;
	float VIRTUAL_SCREEN_WIDTH = 540;
	
	@Override
	public void create () {
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch = new SpriteBatch();

		// Nạp UI Skin từ file.  Chúng ta sử dụng skin mặc định có trong thư mục tests của LibGDX repository trên  Gibhub giống như ở tutorial trước.
		// Đảm bảo những file: default.fnt, default.png, uiskin.[atlas/json/png] đã được thêm trong thư mục assets của project android.
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		stage = new Stage();
		// Sử dụng stage làm input processor vì chúng ta đang sử dụng Scene2D trong ví dụ này
		Gdx.input.setInputProcessor(stage);

		// Đoạn code sau duyệt qua chác Network Interface đang available
		// Có rất nhiều interfaces trên thiết bị. Như là NIC, wireless,...
		// Ở đây chúng ta chỉ quan tâm đến IPv4 address (với format x.x.x.x)
		List<String> addresses = new ArrayList<String>();
		try {
			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
			for(NetworkInterface ni : Collections.list(interfaces)){
				for(InetAddress address : Collections.list(ni.getInetAddresses()))
				{
					if(address instanceof Inet4Address){
						addresses.add(address.getHostAddress());
					}
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}

		// In ra các chuỗi IP Address từ mảng
		String ipAddress = new String("");
		for (String str:addresses) {
			ipAddress = ipAddress + str + "\n";
		}

		// Setup Scene UI

		// Nhóm nội dung theo chiều dọc sử dụng VerticalGroup
		VerticalGroup verticalGroup = new VerticalGroup().space(3).pad(5).fill();

		// Thiết lập các giới hạn (bounds) cho virtual display
		verticalGroup.setBounds(0, 0, VIRTUAL_SCREEN_WIDTH, VIRTUAL_SCREEN_HEIGHT);

		// Tạo các form controls
		labelDetails = new Label(ipAddress, skin);
		labelMessage = new Label("Hello world", skin);
		textIPAddress = new TextArea("", skin);
		textMessage = new TextArea("", skin);
		button = new TextButton("Send message", skin);

		// Add chúng vào scene
		verticalGroup.addActor(labelDetails);
		verticalGroup.addActor(labelMessage);
		verticalGroup.addActor(textIPAddress);
		verticalGroup.addActor(textMessage);
		verticalGroup.addActor(button);

		// Add scene vào stage
		stage.addActor(verticalGroup);

		// Setup viewport
		stage.setViewport(new StretchViewport(VIRTUAL_SCREEN_WIDTH, VIRTUAL_SCREEN_HEIGHT));
		stage.getCamera().position.set(VIRTUAL_SCREEN_WIDTH / 2, VIRTUAL_SCREEN_HEIGHT / 2, 0);

		// Tạo một thread lắng nghe những socket kết nối đến
		new Thread(new Runnable(){

			@Override
			public void run() {
				ServerSocketHints serverSocketHint = new ServerSocketHints();
				// 0 nghĩa là không có timeout.  Chúng ta sẽ không làm như thế này trên production
				serverSocketHint.acceptTimeout = 0;

				// Tạo socket server sử dụng giao thức TCP và lắng nghe cổng 9021
				// Chỉ một ứng dụng lắng nghe một cổng tại một thời gian, bạn có thể chọn một cổng khác chưa được sử dụng bởi ứng dụng khác
				// đặc biệt tránh sử dụng các cổng với số nhỏ ( như 21, 80, ... )
				ServerSocket serverSocket = Gdx.net.newServerSocket(Net.Protocol.TCP, 9021, serverSocketHint);

				// Vòng lặp vô hạn
				while(true){
					// Tạo một socket
					Socket socket = serverSocket.accept(null);

					// Đọc dữ liệu từ socket sử dụng BufferedReader
					BufferedReader buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

					try {
						// Đọc dòng tiếp theo (\n) và hiển thị nội dung trên labelMessage
						labelMessage.setText(buffer.readLine());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}).start(); // Và chạy thread

		// Đăng ký listener cho button
		button.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){

				// Khi button được click, nhận một message hoặc tạo ra một chuỗi mặc
				String textToSend = new String();
				if(textMessage.getText().length() == 0) {
					textToSend = "The button was clicked\n";
				} else {
					textToSend = textMessage.getText() + ("\n");
				}

				SocketHints socketHints = new SocketHints();
				// Socket sẽ timeout trong  4 giây
				socketHints.connectTimeout = 4000;
				// Tạo socket và kết nối tới server được nhập vào trong text box ( theo định dạng x.x.x.x ) trên cổng 9021
				Socket socket = Gdx.net.newClientSocket(Net.Protocol.TCP, textIPAddress.getText(), 9021, socketHints);
				try {
					// Ghi các tin nhắn được nhập vào stream
					socket.getOutputStream().write(textToSend.getBytes());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		stage.draw();
		batch.end();
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
		batch.dispose();
	}
}
