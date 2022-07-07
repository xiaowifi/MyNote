> 当前主要是对于Android MediaFormat class 进行描述，主要是这个类可以用于标记Android 支持硬件解码的视频格式等。
## 资料
* [Google mediaFormat 官网](https://developer.android.com/reference/android/media/MediaFormat)
# 正文
> 这个调调系统版本不一样，里面的key 也不一样。
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
   > 描述关键帧之间的关键帧频率的键，以秒表示。 <p> 此键由视频编码器使用。负值表示在第一帧之后没有请求关键帧。零值表示请求包含所有关键帧的流。 <p class=note> 大多数视频编码器会使用{@linkplain KEY_FRAME_RATE 帧率} 信息来转换关键帧之间的非关键帧数的这个值；因此，如果实际帧率不同（例如输入帧被丢弃或帧率发生变化），关键帧之间的<strong>时间间隔<strong>将不是配置值。 <p> 关联的值是一个整数（或自 {@link android.os.Build.VERSION_CODESN_MR1} 以来的浮点数）。
   > <br> 通俗的讲，可能是I 帧间隔 
*  KEY_INTRA_REFRESH_PERIOD = "intra-refresh-period";
   > 描述帧内刷新周期的可选键。这是一个仅适用于视频编码器的可选参数。如果编码器支持（{@link MediaCodecInfo.CodecCapabilitiesFEATURE_IntraRefresh}），则整个帧在指定周期后完全刷新。同样对于每一帧，宏块的固定子集必须进行帧内编码，这导致比插入关键帧更恒定的比特率。建议将此密钥用于视频流应用程序，因为它提供低延迟和良好的错误恢复能力。如果视频编码器不支持帧内刷新功能，则忽略此键。使用输出格式验证此功能是否已启用。关联的值是一个整数。
*  KEY_PREPEND_HEADER_TO_SYNC_FRAMES = "prepend-sps-pps-to-idr-frames";
   > 一个可选键，描述编码器是否将标头添加到同步帧（例如，SPS 和 PPS 到 H.264 的 IDR 帧）。这是一个仅适用于视频编码器的可选参数。视频编码器可能不支持此功能；在这种情况下，组件将无法配置。对于其他组件，此键将被忽略。该值是一个整数，1 表示将标头添加到每个同步帧，否则为 0。默认值为 0。
*  KEY_TEMPORAL_LAYERING = "ts-schema";
   > 描述时间分层模式的键。这是一个仅适用于视频编码器的可选参数。在 {@link MediaCodecconfigure configure} 之后使用 {@link MediaCodecgetOutputFormat} 来查询编码器是否支持所需的架构。支持的值为 {@code webrtc.vp8.N-layer}、{@code android.generic.N}、{@code android.generic.N+M} 和 {@code none}，其中 {@code N} 表示非双向层的总数（必须至少为 1），{@code M} 表示双向层的总数（必须为非负数）。 <p class=note>{@code android.generic.} 模式已添加到 {@link android.os.Build.VERSION_CODESN_MR1} 中。 <p> 编码器可能支持较少的时间层，在这种情况下，输出格式将包含配置的模式。如果编码器不支持时间分层，则输出格式将没有此键的条目。关联的值是一个字符串。
*  KEY_STRIDE = "stride";
   > 描述视频字节缓冲区布局步幅的键。步幅（或行增量）是像素索引与正下方像素索引之间的差异。对于 YUV 420 格式，步幅对应于 Y 平面； U 和 V 平面的步幅可以根据颜色格式计算，尽管它通常是未定义的并且取决于设备和版本。关联的值是一个整数，表示字节数。
*  KEY_SLICE_HEIGHT = "slice-height";
   > 描述多平面 (YUV) 视频字节缓冲区布局的平面高度的键。切片高度（或平面高度垂直步长）是字节缓冲区中从 Y 平面顶部到 U 平面顶部必须跳过的行数。本质上，U 平面的偏移量是 sliceHeight 步幅。 UV 平面的高度可以根据颜色格式进行计算，尽管它通常是未定义的并且取决于设备和版本。关联的值是一个整数，表示行数。
*  KEY_REPEAT_PREVIOUS_FRAME_AFTER = "repeat-previous-frame-after";
   > 仅在以“surface-input ”模式配置视频编码器时适用。关联的值是 long 并给出以微秒为单位的时间，如果此后没有可用的新帧，则先前提交给编码器的帧将重复（一次）。
*  KEY_MAX_FPS_TO_ENCODER = "max-fps-to-encoder";
   >指示处于“surface-input”模式的视频编码器从源中丢弃过多的帧，以便编码器的输入帧速率不超过指定的 fps。关联的值是一个浮点数，表示输入编码器的最大帧速率。
*  KEY_MAX_PTS_GAP_TO_ENCODER = "max-pts-gap-to-encoder";
   > 指示处于“表面输入”模式的视频编码器将馈送到编码器的任何两个相邻帧之间的时间戳间隙限制在指定的量（以微秒为单位）。关联的值是一个 long int。当为正时，它表示馈送到编码器的两个相邻帧之间的最大时间戳间隙。当为负时，绝对值表示馈送到编码器的任何两个相邻帧之间的固定时间戳间隙。请注意，即使原始时间戳在时间上倒退，这也将适用。在正常情况下，此类帧将被丢弃并且不会发送到编码器。输出时间戳会恢复到原来的时间戳，不受影响。这用于输入帧很少到达但不希望为任何单个帧分配更多位的特殊场景，或者当确保捕获所有帧（而不是按正确顺序捕获）很重要时。
*  KEY_CREATE_INPUT_SURFACE_SUSPENDED = "create-input-buffers-suspended";
   >如果在配置处于“表面输入”模式的视频编码器时指定，它将指示编码器在连接时将表面源置于暂停状态。在接收到恢复操作（请参阅 {@link MediaCodecPARAMETER_KEY_SUSPEND}）之前，不会接受任何视频帧，可选择通过 {@link MediaCodecPARAMETER_KEY_SUSPEND_TIME} 指定时间戳。该值是一个整数，1 表示在表面源暂停的情况下创建，否则为 0。默认值为 0。如果此键未设置或设置为 0，则表面源将在连接到编码器后立即接受缓冲区（尽管它们可能不会立即编码）。当客户端想要提前准备编码器会话但不想立即接受缓冲区时，可以使用此密钥。
*  KEY_PUSH_BLANK_BUFFERS_ON_STOP = "push-blank-buffers-on-shutdown";
   > 如果在配置视频解码器渲染到表面时指定，则导致解码器在停止以清除任何先前显示的内容时输出“空白”，即向表面输出黑帧。关联值是值为 1 的整数。
*  KEY_DURATION = "durationUs";
   > 描述内容持续时间（以微秒为单位）的键。关联的值为 long。
*  KEY_IS_ADTS = "is-adts";
   > 如果内容是 AAC 音频并且音频帧以 ADTS 标头为前缀，则键映射到值 1。关联的值是一个整数（0 或 1）。此键仅在 _decoding_ 内容时支持，不能用于配置编码器以发出 ADTS 输出。
*  KEY_CHANNEL_MASK = "channel-mask";
   > 描述音频内容的通道组成的键。此掩码由 {@link android.media.AudioFormat} 中的通道掩码定义中提取的位组成。关联的值是一个整数。
*  KEY_ENCODER_DELAY = "encoder-delay";
   > 描述从解码的音频流开始修剪的帧数的键。关联的值是一个整数。
*  KEY_ENCODER_PADDING = "encoder-padding";
   > 描述从解码的音频流末尾修剪的帧数的键。关联的值是一个整数。
*  KEY_AAC_PROFILE = "aac-profile";
   > 描述要使用的 AAC 配置文件的密钥（仅限 AAC 音频格式）。常量在 {@link android.media.MediaCodecInfo.CodecProfileLevel} 中声明。
*  KEY_AAC_SBR_MODE = "aac-sbr-mode";
   > 描述要使用的 AAC SBR 模式的密钥（仅限 AAC 音频格式）。相关值是一个整数，可以设置为以下值： <ul> <li>0 - 不应应用 SBR<li> <li>1 - 单速率 SBR<li> <li>2 - 双速率 SBR< li> <ul> 注意：如果未定义此键，则将使用所需 AAC 配置文件的默认 SRB 模式。 <p>此密钥仅在编码期间使用。
*  KEY_AAC_MAX_OUTPUT_CHANNEL_COUNT = "aac-max-output-channel_count";
   > 描述 AAC 解码器可以输出的最大通道数的键。默认情况下，如果支持，解码器将输出与编码流中相同数量的通道。设置此值以限制输出通道的数量，并使用流中的缩混信息（如果可用）。 <p>大于内容中要解码的通道数的值将被忽略。 <p>此密钥仅在解码期间使用。
*  KEY_AAC_DRC_TARGET_REFERENCE_LEVEL = "aac-target-ref-level";
   > 描述目标参考电平（目标响度）的键。 <p>为了使节目项目的响度标准化，将增益应用于音频输出，以使输出响度与目标参考电平相匹配。增益是作为目标参考电平和节目参考电平（节目响度）之间的差异得出的。后者可以在比特流中给出并指示节目项目的实际响度值。<p> <p>目标参考电平控制 MPEG-4 DRC 和 MPEG-D DRC 的响度归一化。 <p>该值以 40 到 127 之间的整数值形式给出，在 LKFS 中计算为 -4 目标参考水平。因此，它代表 -10 到 -31.75 LKFS 的范围。 <p>对于 MPEG-4 DRC，-1 值关闭响度归一化和 DRC 处理。<p> <p>对于 MPEG-D DRC，-1 值仅关闭响度归一化。有关 MPEG-D DRC 的 DRC 处理选项，请参阅 {@link KEY_AAC_DRC_EFFECT_TYPE}<p> <p>移动设备上的默认值为 64 (-16 LKFS)。 <p>此密钥仅在解码期间使用。
*  KEY_AAC_DRC_EFFECT_TYPE = "aac-drc-effect-type";
   >描述为 MPEG-D DRC 选择 DRC 效果类型的键。支持的值在 ISOIEC 23003-4:2015 中定义并描述如下： <table> <tr><th>Value<th><th>Effect<th><tr> <tr><th>-1< th><th>关闭<th><tr> <tr><th>0<th><th>无<th><tr> <tr><th>1<th><th>深夜<th> <tr> <tr><th>2<th><th>嘈杂的环境<th><tr> <tr><th>3<th><th>播放范围有限<th><tr> <tr>< th>4<th><th>低播放级别<th><tr> <tr><th>5<th><th>对话增强<th><tr> <tr><th>6<th>< th>常规压缩<th><tr> <table> <p>值 -1（关闭）禁用 DRC 处理，而响度归一化可能仍处于活动状态并取决于 {@link KEY_AAC_DRC_TARGET_REFERENCE_LEVEL}。<br> 值 0（无）在必要时自动启用 DRC 处理以防止信号削波<br> 值 6（常规压缩）可用于启用 MPEG-D DRC，无需特定 DRC 效果类型请求。<br> 默认 DRC 效果类型为 3 ("有限的播放范围”）在移动设备上。 <p>此密钥仅在解码期间使用。
*  KEY_AAC_ENCODED_TARGET_LEVEL = "aac-encoded-target-level";
   > 描述在编码器处假定的目标参考电平的键，用于计算用于削波预防的衰减增益。 <p>如果已知，则此信息可以作为 0 到 127 之间的整数值提供，在 LKFS 中计算为 -4 Encoded Target Level。如果 Encoded Target Level 未知，则可以将该值设置为 -1。 <p>默认值为 -1（未知）。 <p>使用重压缩（请参阅 {@link KEY_AAC_DRC_HEAVY_COMPRESSION}）或 MPEG-D DRC 时忽略该值。 <p>此密钥仅在解码期间使用。
*  KEY_AAC_DRC_BOOST_FACTOR = "aac-drc-boost-level";
   > 一个描述提升因子的键，允许调整输出的动态以适应实际的聆听要求。这依赖于可以在编码比特流中传输的 DRC 增益序列，以便能够根据请求减少输出信号的动态。该因素使用户能够选择应用多少增益。 <p>正增益（提升）和负增益（衰减，请参阅 {@link KEY_AAC_DRC_ATTENUATION_FACTOR}）可以单独控制，以更好地匹配不同的用例。 <p>通常，衰减增益用于发送响亮的信号段，而增强增益用于发送较弱的信号段。例如，如果在嘈杂的环境中收听输出，则使用提升因子来启用正增益，即放大超出本底噪声的软信号段。但是对于深夜收听，衰减因子用于启用负增益，以防止响亮的信号使听众感到惊讶。在通常需要低动态范围的应用中，为了启用所有 DRC 增益，会同时使用提升因子和衰减因子。 <p>为了防止削波，还建议在缩混和/或响度归一化的情况下将衰减增益应用于高目标参考电平。 <p>boost 和衰减因子参数均以 0 到 127 之间的整数值形式给出，表示因子 0（即不应用）到 1（即分别完全应用 boostattenuation 增益）的范围。 <p>默认值为 127（完全应用增强 DRC 增益）。 <p>此密钥仅在解码期间使用。
*  KEY_AAC_DRC_ATTENUATION_FACTOR = "aac-drc-cut-level";
   > 一个描述衰减因子的键，允许使输出的动态适应实际的聆听要求。有关此衰减因子的作用和取值范围的说明，请参见 {@link KEY_AAC_DRC_BOOST_FACTOR}。 <p>默认值为 127（完全应用衰减 DRC 增益）。 <p>此密钥仅在解码期间使用。
*  KEY_AAC_DRC_HEAVY_COMPRESSION = "aac-drc-heavy-compression";
   > 描述为 MPEG-4 DRC 选择重压缩配置文件的关键。 <p>两个独立的 DRC 增益序列可以在一个比特流中传输：轻压缩和重压缩。在选择重压缩的应用时，选择以下序列之一：<ul> <li>0 启用轻压缩，<li> <li>1 启用重压缩。 <ul> 请注意，重压缩不提供缩放 DRC 增益的功能（请参阅 {@link KEY_AAC_DRC_BOOST_FACTOR} 和 {@link KEY_AAC_DRC_ATTENUATION_FACTOR} 以了解增强和衰减因子）和频率选择性（多频段）DRC。轻度压缩通常包含针对立体声缩混的削波预防，而重型压缩（如果在比特流中额外提供）通常更强，并且包含针对立体声和单声道缩混的削波预防。 <p>默认值为 1（高度压缩）。
* KEY_AAC_DRC_OUTPUT_LOUDNESS = "aac-drc-output-loudness";
   > 检索解码比特流的输出响度的密钥。 <p>如果响度归一化处于活动状态，则该值对应于目标参考电平（请参阅 {@link KEY_AAC_DRC_TARGET_REFERENCE_LEVEL}）。<br> 如果响度归一化未激活，则该值对应于比特流中给出的响度元数据。 <p>使用 getInteger() 检索该值，并以 0 到 231 之间的整数值给出。它在 LKFS 中计算为 -4 输出响度。因此，它代表 0 到 -57.75 LKFS 的范围。 <p>-1 值表示比特流中不存在响度元数据。 <p>响度元数据可以源自 MPEG-4 DRC 或 MPEG-D DRC。 <p>此密钥仅在解码期间使用。
* KEY_AAC_DRC_ALBUM_MODE = "aac-drc-album-mode";
   > 描述 ISOIEC 23003-4 中定义的 MPEG-D DRC 专辑模式的密钥。 <p>关联值是一个整数，可以设置为以下值： <table> <tr><th>Value<th><th>专辑模式<th><tr> <tr><th>0<th ><th>disabled<th><tr> <tr><th>1<th><th>enabled<th><tr> <table> <p>Disabled 专辑模式导致应用增益序列来淡入和出，如果在比特流中提供。启用的专辑模式使用专用专辑响度信息（如果在比特流中提供）。 <p>默认值为 0（禁用相册模式）。 <p>此密钥仅在解码期间使用。
*  KEY_FLAC_COMPRESSION_LEVEL = "flac-compression-level";
   > 描述要使用的 FLAC 压缩级别的键（仅限 FLAC 音频格式）。关联的值是一个整数，范围从 0（最快，最少压缩）到 8（最慢，最多压缩）。
*  KEY_COMPLEXITY = "complexity";
   > 描述编码复杂性的键。关联的值是一个整数。这些值是特定于设备和编解码器的，但较低的值通常会导致更快或更少耗电的编码。 @see MediaCodecInfo.EncoderCapabilitiesgetComplexityRange()
*  KEY_QUALITY = "quality";
   > 描述所需编码质量的键。关联的值是一个整数。仅在恒定质量模式下配置的编码器支持此键。这些值是特定于设备和编解码器的，但较低的值通常会导致更有效（更小）的编码。 @see MediaCodecInfo.EncoderCapabilitiesgetQualityRange()
*  KEY_PRIORITY = "priority";
   > 描述所需编解码器优先级的键。 <p> 关联的值是一个整数。较高的值意味着较低的优先级。 <p> 目前，仅支持两个级别：<br> 0：实时优先级 - 意味着编解码器应实时支持给定的性能配置（例如帧速率）。这应该只用于媒体播放、捕获，如果尽力而为性能不合适，还可能用于实时通信场景。<br> 1：非实时优先级（尽力而为）。 <p> 这是用于编解码器配置和资源规划的提示 - 了解应用程序的实时要求；但是，由于媒体组件的性质，不能保证性能。
*  KEY_OPERATING_RATE = "operating-rate";
   > 描述编解码器运行所需的视频操作帧率或音频采样率的键。 <p> 相关值是整数或浮点数，表示每秒帧数或每秒采样数 <p> 这用于高速慢动作视频捕获等情况，其中视频编码器格式包含目标播放率（例如 30fps），但组件必须能够处理高操作捕获率（例如 240fps）。 <p> 编解码器将使用此速率进行资源规划和设置操作点。
*  KEY_PROFILE = "profile";
   > 描述编码器使用的所需配置文件的密钥。关联的值是一个整数。常量在 {@link MediaCodecInfo.CodecProfileLevel} 中声明。此键用作提示，仅支持指定配置文件的编解码器。注意：编解码器可以免费使用指定配置文件中的所有可用编码工具。
*  KEY_LEVEL = "level";
   > 描述编码器使用的所需配置文件的密钥。关联的值是一个整数。常量在 {@link MediaCodecInfo.CodecProfileLevel} 中声明。此键在指定所需配置文件时用作进一步提示，并且仅支持指定级别的编解码器。 <p> 如果未指定 {@link KEY_PROFILE 配置文件}，则忽略此键。 @see MediaCodecInfo.CodecCapabilitiesprofileLevels
*  KEY_LATENCY = "latency";
   > 描述所需编码器延迟的可选键（以帧为单位）。这是一个仅适用于视频编码器的可选参数。如果编码器支持它，它应该在排队指定数量的帧后输出至少一个输出帧。如果视频编码器不支持延迟功能，则忽略此键。使用输出格式验证此功能是否已启用以及编码器使用的实际值。 <p> 如果未指定密钥，则默认延迟将是特定于实现的。关联的值是一个整数。
*  KEY_OUTPUT_REORDER_DEPTH = "output-reorder-depth";
   > 描述非显示顺序编码帧的最大数量的可选键。这是一个仅适用于视频编码器的可选参数。应用程序应检查输出格式中此键的值，以查看编解码器是否会生成非显示顺序编码帧。如果编码器支持它，输出帧的顺序将与显示顺序不同，并且每个帧的显示顺序可以从 {@link MediaCodec.BufferInfopresentationTimeUs} 中检索。在 API 级别 27 之前，即使应用程序没有请求，应用程序也可能会收到非显示顺序编码帧。注意：应用程序在将它们提供给 {@link MediaMuxerwriteSampleData} 之前不应重新排列帧以显示顺序。 <p> 默认值为 0。
*  KEY_ROTATION = "rotation-degrees";
   > 描述输出表面上所需的顺时针旋转的键。此键仅在使用输出表面配置编解码器时使用。关联的值是一个整数，表示度数。支持的值为 0、90、180 或 270。这是一个可选字段；如果未指定，则旋转默认为 0。@see MediaCodecInfo.CodecCapabilitiesprofileLevels
*  KEY_BITRATE_MODE = "bitrate-mode";常用的模式是VBR,CBR。 
   > 描述编码器使用的所需比特率模式的密钥。常量在 {@link MediaCodecInfo.CodecCapabilities} 中声明。 @see MediaCodecInfo.EncoderCapabilitiesisBitrateModeSupported(int)
*  KEY_AUDIO_SESSION_ID = "audio-session-id";
   > 描述与隧道视频编解码器关联的 AudioTrack 的音频会话 ID 的键。关联的值是一个整数。 @see MediaCodecInfo.CodecCapabilitiesFEATURE_TunneledPlayback
*  KEY_IS_AUTOSELECT = "is-autoselect";
   > 用于轨道的布尔自动选择行为的键。根据当前区域设置自动选择没有特定用户选择的轨道时，会考虑具有 AUTOSELECT=true 的轨道。当用户为字幕语言环境选择“默认”时，这目前仅用于字幕轨道。关联的值是一个整数，其中非 0 表示 TRUE。这是个可选的选项;如果未指定，AUTOSELECT 默认为 TRUE。
*  KEY_IS_DEFAULT = "is-default";
   > 轨道的布尔默认行为的键。在没有特定用户选择的情况下选择 DEFAULT=true 的轨道。这目前在两种情况下使用：1) 用于字幕轨道，当用户为字幕语言环境选择“默认”时。 2) 对于 {@link MIMETYPE_IMAGE_ANDROID_HEIC} 轨道，指示图像是文件中的主要项目。关联的值是一个整数，其中非 0 表示 TRUE。这是个可选的选项;如果未指定，则 DEFAULT 被认为是 FALSE。
*  KEY_IS_FORCED_SUBTITLE = "is-forced-subtitle";
   > 字幕轨道的 FORCED 字段的键。如果是强制字幕轨道，则为真。强制字幕轨道对内容至关重要，即使用户关闭字幕也会显示。例如，它们用于翻译外国人的对话或标志。关联的值是一个整数，其中非 0 表示 TRUE。这是个可选的选项;如果未指定，则 FORCED 默认为 FALSE。
*  KEY_HAPTIC_CHANNEL_COUNT = "haptic-channel-count";
   > 描述音频格式中触觉通道数量的键。关联的值是一个整数。
*  KEY_IS_TIMED_TEXT = "is-timed-text";
   > 这个调调没有注释，不知道干啥的 
*  KEY_COLOR_STANDARD = "color-standard";
   > 描述视频内容的原色、白点和亮度因子的可选键。关联值是一个整数：如果未指定，则为 0，或者 COLOR_STANDARD_ 值之一。
*  KEY_COLOR_TRANSFER = "color-transfer";
   > 描述用于视频内容的光电传输函数的可选密钥。关联的值是一个整数：如果未指定，则为 0，或者 COLOR_TRANSFER_ 值之一。
*  KEY_COLOR_RANGE = "color-range";
   > 描述视频内容的组件值范围的可选键。关联值是一个整数：如果未指定，则为 0，或者 COLOR_RANGE_ 值之一。
*  KEY_HDR_STATIC_INFO = "hdr-static-info";
   > 描述 HDR（高动态范围）视频内容的静态元数据的可选键。关联的值是一个 ByteBuffer。此缓冲区包含 CTA-861.3 定义的 HDMI 动态范围和主控信息帧的静态元数据描述符（包括描述符 ID）的原始内容。必须将此密钥提供给 HDR 视频内容的视频解码器，除非此信息包含在比特流中并且视频解码器支持支持 HDR 的配置文件。必须将此密钥提供给 HDR 视频内容的视频编码器。
*  KEY_HDR10_PLUS_INFO = "hdr10-plus-info";
   > 描述视频内容的 HDR10+ 元数据的可选键。相关值是一个 ByteBuffer，包含符合 ST 2094-40 的 SEI 消息的 user_data_registered_itu_t_t35() 语法的 HDR10+ 元数据。此键将出现在：<p> - 为 HDR10+ 配置文件配置的解码器的输出缓冲区格式（例如 {@link MediaCodecInfo.CodecProfileLevelVP9Profile2HDR10Plus}、{@link MediaCodecInfo.CodecProfileLevelVP9Profile3HDR10Plus} 或 {@link MediaCodecInfo.CodecProfileLevelHEVCProfileMain10HDR10Plus}） , 或 <p> - 为使用带外元数据的 HDR10+ 配置文件配置的编码器的输出缓冲区格式（例如 {@link MediaCodecInfo.CodecProfileLevelVP9Profile2HDR10Plus} 或 {@link MediaCodecInfo.CodecProfileLevelVP9Profile3HDR10Plus}）。 @see MediaCodecPARAMETER_KEY_HDR10_PLUS_INFO
*  KEY_TRACK_ID = "track-id";
   > 描述媒体轨道内容的唯一 ID 的键。 <p>此密钥由 {@link MediaExtractor} 使用。一些提取器提供同一轨道的多种编码（例如，FLAC 和 WAV 的浮动音轨可以通过 MediaExtractor 表示为两个轨道：一个用于向后兼容的普通 PCM 轨道，一个用于增加保真度的浮动 PCM 轨道。类似地，杜比视界提取器可以提供 DV 轨道的基线 SDR 版本。）此密钥可用于识别哪些 MediaExtractor 轨道引用相同的基础内容。 <p> 关联的值是一个整数。
*  KEY_CA_SYSTEM_ID = "ca-system-id";
   > 描述用于加扰媒体轨道的条件访问系统的系统 ID 的密钥。 <p> 如果轨道使用条件访问系统加扰，则此键由 {@link MediaExtractor} 设置，无论是否存在有效的 {@link MediaCas} 对象。 <p> 关联的值是一个整数。
*  KEY_CA_SESSION_ID = "ca-session-id";
   > 描述与媒体轨道关联的 {@link MediaCas.Session} 对象的键。 <p> 如果轨道使用条件访问系统加扰，则此密钥由 {@link MediaExtractor} 在收到有效的 {@link MediaCas} 对象后设置。 <p> 关联的值是一个 ByteBuffer。
*  KEY_CA_PRIVATE_DATA = "ca-private-data";
   >描述 CA_descriptor 中与媒体轨道关联的私有数据的密钥。 <p> 如果轨道在接收到有效的 {@link MediaCas} 对象之前使用条件访问系统加扰，则此密钥由 {@link MediaExtractor} 设置。 <p> 关联的值是一个 ByteBuffer。
*  KEY_MAX_B_FRAMES = "max-bframes";
   > 描述 I 或 P 帧之间的最大 B 帧数的密钥，供视频编码器使用。关联的值是一个整数。默认值为 0，表示不允许 B 帧。请注意，非零值不保证 B 帧；由编码器决定。
*  KEY_FEATURE_ = "feature-";
   > 与 {@link MediaCodecInfo.CodecCapabilities} 功能名称一起使用的键前缀，描述编解码器功能查询的必需或可选功能。关联值是一个整数，其中非 0 值表示请求存在该特征，而 0 值表示请求不存在该特征。 @see MediaCodecListfindDecoderForFormat @see MediaCodecListfindEncoderForFormat @see MediaCodecInfo.CodecCapabilitiesisFormatSupported