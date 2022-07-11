## 示例代码
```aidl
 try {
            AACTrackImpl aacTrack = new AACTrackImpl(new FileDataSourceImpl("C:\\content\\Cosmos Laundromat small.aac"));
            H264TrackImpl h264Track = new H264TrackImpl(new FileDataSourceImpl("C:\\content\\Cosmos Laundromat small.264"));
            Movie m = new Movie();
            m.addTrack(aacTrack);
            m.addTrack(h264Track);
            DefaultMp4Builder mp4Builder = new DefaultMp4Builder();
            Container out = mp4Builder.build(m);
            FileOutputStream fos = new FileOutputStream("output.mp4");
            FileChannel fc = fos.getChannel();
            out.writeContainer(fc);

            fos.close();
        }catch (Exception e){

        }

    }
```