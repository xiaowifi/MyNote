> 当前主要是对于Android MediaFormat class 进行描述，主要是这个类可以用于标记Android 支持硬件解码的视频格式等。
## 资料
* [Google mediaFormat 官网](https://developer.android.com/reference/android/media/MediaFormat)
# 正文
## 视频
*  MIMETYPE_VIDEO_VP8 = "video/x-vnd.on2.vp8"; vp8 格式
*  MIMETYPE_VIDEO_VP9 = "video/x-vnd.on2.vp9"; vp9 格式
*  MIMETYPE_VIDEO_AV1 = "video/av01";AV1 是一种新兴的开源免版税视频压缩格式，由开放多媒体联盟 (AOMedia) 行业联盟于 2018 年初联合开发并最终确定。AV1 开发的主要目标是在保持实际解码复杂性和硬件可行性的同时，在最先进的编解码器上实现显着的压缩增益
*  MIMETYPE_VIDEO_AVC = "video/avc";H264
*  MIMETYPE_VIDEO_HEVC = "video/hevc";H265
*  MIMETYPE_VIDEO_MPEG4 = "video/mp4v-es"; MPEG4
*  MIMETYPE_VIDEO_H263 = "video/3gpp";H263
*  MIMETYPE_VIDEO_MPEG2 = "video/mpeg2";
*  MIMETYPE_VIDEO_RAW = "video/raw";
*  MIMETYPE_VIDEO_DOLBY_VISION = "video/dolby-vision";
*  MIMETYPE_VIDEO_SCRAMBLED = "video/scrambled";
## 音频 
*  MIMETYPE_AUDIO_AMR_NB = "audio/3gpp";AMR
*  MIMETYPE_AUDIO_AMR_WB = "audio/amr-wb";
*  MIMETYPE_AUDIO_MPEG = "audio/mpeg";
*  MIMETYPE_AUDIO_AAC = "audio/mp4a-latm";
*  MIMETYPE_AUDIO_QCELP = "audio/qcelp";
*  MIMETYPE_AUDIO_VORBIS = "audio/vorbis";
*  MIMETYPE_AUDIO_OPUS = "audio/opus";
*  MIMETYPE_AUDIO_G711_ALAW = "audio/g711-alaw";
*  MIMETYPE_AUDIO_G711_MLAW = "audio/g711-mlaw";
*  MIMETYPE_AUDIO_RAW = "audio/raw";
*  MIMETYPE_AUDIO_FLAC = "audio/flac";
*  MIMETYPE_AUDIO_MSGSM = "audio/gsm";
*  MIMETYPE_AUDIO_AC3 = "audio/ac3";
*  MIMETYPE_AUDIO_EAC3 = "audio/eac3";
*  MIMETYPE_AUDIO_EAC3_JOC = "audio/eac3-joc";
*  MIMETYPE_AUDIO_AC4 = "audio/ac4";
*  MIMETYPE_AUDIO_SCRAMBLED = "audio/scrambled";
## 其他
*  MIMETYPE_IMAGE_ANDROID_HEIC = "image/vnd.android.heic"; 
   > 静态图片
*  MIMETYPE_TEXT_VTT = "text/vtt";
   > 字幕
*  MIMETYPE_TEXT_SUBRIP = "application/x-subrip";
*  MIMETYPE_TEXT_CEA_608 = "text/cea-608";
*  MIMETYPE_TEXT_CEA_708 = "text/cea-708";
## 提供字段
*  KEY_MIME = "mime";
   > 当前轨道类型，值为上述视频，音频，其他中的类型。
*  KEY_LANGUAGE = "language";
   > 语言 
*  KEY_SAMPLE_RATE = "sample-rate";
   > 描述音频格式的采样率的键。关联的值是一个整数
*  KEY_CHANNEL_COUNT = "channel-count";
   > 描述音频格式通道数的键。关联的值是一个整数
*  KEY_WIDTH = "width";
   > 描述视频格式内容宽度的键。关联的值是一个整数
*  KEY_HEIGHT = "height";
   > 描述视频格式内容高度的键。关联的值是一个整数
*  KEY_MAX_WIDTH = "max-width";
   > 描述视频解码器格式中内容的最大预期宽度的键，以防视频内容中的分辨率发生变化。关联的值是一个整数
*  KEY_MAX_HEIGHT = "max-height";
   > 描述视频解码器格式中内容的最大预期高度的键，以防视频内容中的分辨率发生变化。关联的值是一个整数
*  KEY_MAX_INPUT_SIZE = "max-input-size";
   > 描述此 MediaFormat 描述的数据缓冲区的最大大小（以字节为单位）的键。关联的值是一个整数
*  KEY_MAX_INPUT_SIZE = "max-input-size";
   > 以比特秒为单位描述平均比特率的键。关联的值是一个整数
*  KEY_MAX_BIT_RATE = "max-bitrate";
   > 描述最大比特率的密钥，以比特秒为单位。这通常超过一秒的滑动窗口（例如超过一秒的任何窗口）。关联的值是一个整数
*  KEY_COLOR_FORMAT = "color-format";
   > 描述视频格式内容颜色格式的键。  android.media.MediaCodecInfo.CodecCapabilities
*  KEY_FRAME_RATE = "frame-rate";
   > 以帧秒为单位描述视频格式的帧速率的键。当平台使用该值时，关联的值通常是一个整数，但视频编解码器也接受浮点配置值。具体来说，{@link MediaExtractorgetTrackFormat MediaExtractor} 提供与轨道的帧速率信息相对应的整数值（如果指定且非零）。否则，此密钥不存在。 {@link MediaCodecconfigure MediaCodec} 接受浮点值和整数值。如果 {@link KEY_OPERATING_RATE} 不存在且 {@link KEY_PRIORITY} 为 {@code 0}（实时），则这表示所需的操作帧速率。对于视频编码器，此值对应于预期的帧速率，尽管编码器应支持基于 {@link MediaCodec.BufferInfopresentationTimeUs 缓冲区时间戳}的可变帧速率。 {@code MediaCodec} {@link MediaCodecgetInputFormat input}{@link MediaCodecgetOutputFormat output} 格式以及 {@link MediaMuxeraddTrack MediaMuxer} 均未使用此密钥。
*  KEY_TILE_WIDTH = "tile-width";
   > 描述 {@link MIMETYPE_IMAGE_ANDROID_HEIC} 轨道中内容的每个图块的宽度（以像素为单位）的键。关联的值是一个整数。有关此类曲目的解码说明，请参阅 {@link MIMETYPE_IMAGE_ANDROID_HEIC}。 @see KEY_TILE_HEIGHT @see KEY_GRID_ROWS @see KEY_GRID_COLUMNS
*  KEY_TILE_HEIGHT = "tile-height";
   > 描述 {@link MIMETYPE_IMAGE_ANDROID_HEIC} 轨道中内容的每个图块的高度（以像素为单位）的键。关联的值是一个整数。有关此类曲目的解码说明，请参阅 {@link MIMETYPE_IMAGE_ANDROID_HEIC}。 @see KEY_TILE_WIDTH @see KEY_GRID_ROWS @see KEY_GRID_COLUMNS
*  KEY_GRID_ROWS = "grid-rows";
   > 描述 {@link MIMETYPE_IMAGE_ANDROID_HEIC} 轨道内容中网格行数的键。关联的值是一个整数。有关此类曲目的解码说明，请参阅 {@link MIMETYPE_IMAGE_ANDROID_HEIC}。 @see KEY_TILE_WIDTH @see KEY_TILE_HEIGHT @see KEY_GRID_COLUMNS
*  KEY_GRID_COLUMNS = "grid-cols";
   > 描述 {@link MIMETYPE_IMAGE_ANDROID_HEIC} 轨道内容中网格列数的键。关联的值是一个整数。有关此类曲目的解码说明，请参阅 {@link MIMETYPE_IMAGE_ANDROID_HEIC}。 @see KEY_TILE_WIDTH @see KEY_TILE_HEIGHT @see KEY_GRID_ROWS
*  KEY_PCM_ENCODING = "pcm-encoding";
   > 描述原始音频样本编码格式的键。 <p>相关值是一个整数，使用 {@link AudioFormat}.ENCODING_PCM_ 值之一。<p> <p>这是音频解码器和编码器的可选键，在 {@link 期间指定所需的原始音频样本格式MediaCodecconfigure MediaCodec.configure(…)} 调用。使用 {@link MediaCodecgetInputFormat MediaCodec.getInput}{@link MediaCodecgetOutputFormat OutputFormat(…)} 来确认实际格式。对于 PCM 解码器，此键指定输入和输出样本编码。<p> <p>{@link MediaExtractor} 也使用此键指定音频数据的样本格式（如果已指定）。<p> <p >如果缺少此密钥，则原始音频样本格式为 16 位短签名
*  KEY_CAPTURE_RATE = "capture-rate";
   > 以帧秒为单位描述视频格式的捕获率的键。 <p> 当采集速率与帧速率不同时，表示视频的采集速率与播放速率不同，在播放过程中会产生慢动作或延时摄影效果。应用程序可以使用此键的值来判断录制视频时捕获和播放速率之间的相对速度比。 <p> <p> 关联的值是整数或浮点数。
*  KEY_I_FRAME_INTERVAL = "i-frame-interval";
   > 
*  KEY_INTRA_REFRESH_PERIOD = "intra-refresh-period";
   > 
*  KEY_PREPEND_HEADER_TO_SYNC_FRAMES = "prepend-sps-pps-to-idr-frames";
   > 
*  KEY_TEMPORAL_LAYERING = "ts-schema";
*  KEY_STRIDE = "stride";
*  KEY_SLICE_HEIGHT = "slice-height";
*  KEY_REPEAT_PREVIOUS_FRAME_AFTER = "repeat-previous-frame-after";
*  KEY_MAX_FPS_TO_ENCODER = "max-fps-to-encoder";
*  KEY_MAX_PTS_GAP_TO_ENCODER = "max-pts-gap-to-encoder";
*  KEY_CREATE_INPUT_SURFACE_SUSPENDED = "create-input-buffers-suspended";
*  KEY_PUSH_BLANK_BUFFERS_ON_STOP = "push-blank-buffers-on-shutdown";
*  KEY_DURATION = "durationUs";
*  KEY_IS_ADTS = "is-adts";
*  KEY_CHANNEL_MASK = "channel-mask";
*  KEY_AAC_PROFILE = "aac-profile";
*  KEY_AAC_SBR_MODE = "aac-sbr-mode";
*  KEY_AAC_MAX_OUTPUT_CHANNEL_COUNT = "aac-max-output-channel_count";
*  KEY_AAC_DRC_TARGET_REFERENCE_LEVEL = "aac-target-ref-level";
*  KEY_AAC_DRC_EFFECT_TYPE = "aac-drc-effect-type";
*  KEY_AAC_ENCODED_TARGET_LEVEL = "aac-encoded-target-level";
*  KEY_AAC_DRC_BOOST_FACTOR = "aac-drc-boost-level";
*  KEY_AAC_DRC_ATTENUATION_FACTOR = "aac-drc-cut-level";
*  KEY_AAC_DRC_HEAVY_COMPRESSION = "aac-drc-heavy-compression";
*  KEY_FLAC_COMPRESSION_LEVEL = "flac-compression-level";
*  KEY_COMPLEXITY = "complexity";
*  KEY_QUALITY = "quality";
*  KEY_PRIORITY = "priority";
*  KEY_OPERATING_RATE = "operating-rate";
*  KEY_PROFILE = "profile";
*  KEY_LEVEL = "level";
*  KEY_LATENCY = "latency";
*  KEY_OUTPUT_REORDER_DEPTH = "output-reorder-depth";
*  KEY_ROTATION = "rotation-degrees";
*  KEY_BITRATE_MODE = "bitrate-mode";
*  KEY_AUDIO_SESSION_ID = "audio-session-id";
*  KEY_IS_AUTOSELECT = "is-autoselect";
*  KEY_IS_DEFAULT = "is-default";
*  KEY_IS_FORCED_SUBTITLE = "is-forced-subtitle";
*  KEY_HAPTIC_CHANNEL_COUNT = "haptic-channel-count";
*  KEY_IS_TIMED_TEXT = "is-timed-text";
*  KEY_COLOR_STANDARD = "color-standard";
*  KEY_COLOR_TRANSFER = "color-transfer";
*  KEY_COLOR_RANGE = "color-range";
*  KEY_HDR_STATIC_INFO = "hdr-static-info";
*  KEY_HDR10_PLUS_INFO = "hdr10-plus-info";
*  KEY_TRACK_ID = "track-id";
*  KEY_CA_SYSTEM_ID = "ca-system-id";
*  KEY_CA_SESSION_ID = "ca-session-id";
*  KEY_CA_PRIVATE_DATA = "ca-private-data";
*  KEY_MAX_B_FRAMES = "max-bframes";
*  KEY_FEATURE_ = "feature-";