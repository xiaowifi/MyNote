## 代码示例
```java
        MediaController mediaController = new MediaController(requireContext());
        mediaController.setAnchorView(videoView);
        videoView.setVideoURI(Uri.parse("file://" + getVideoFile().getAbsolutePath()));
        videoView.setMediaController(mediaController);
        videoView.requestFocus();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            @Override
            public void onPrepared(MediaPlayer mp) {
                videoView.setBackgroundColor(0x00000000);
            }
        });
        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {

            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                videoView.stopPlayback();
                return false;
            }
        });
        videoView.start();
```