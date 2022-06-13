> editText 是基于TextView 封装而来。AppCompatEditText 则是基于EditText 封装。
## 资料
* [TextView](https://developer.android.com/reference/android/widget/TextView)
* [EditText](https://developer.android.com/reference/android/widget/EditText?hl=en)
* [AppCompatEditText](https://developer.android.com/reference/androidx/appcompat/widget/AppCompatEditText?hl=en)
# 正文 

## 属性
### imeOptions
您可以在与编辑器关联的 IME 中启用其他功能，以改进与您的应用程序的集成。这里的常量对应于 {@link android.view.inputmethod.EditorInfoimeOptions} 定义的常量。

| 值                | 含义 |
| :---------------- | ---- |
| normal            | 没有与此编辑器相关的特殊语义 |
| actionUnspecified | 这个编辑器没有具体的动作，可以的话让编辑器自己想办法。 |
| actionNone        | 此编辑器没有与之关联的操作 |
| actionGo          | 操作键执行“go”操作以将用户带到他们键入的文本的目标。例如，通常在输入 URL 时使用 |
| actionSearch      | 操作键执行“搜索”操作，将用户带到搜索已键入文本的结果（在任何适当的上下文中）。 |
| actionSend        | 操作键执行“发送”操作，将文本传送到其目标。这通常在撰写消息时使用 |
| actionNext        | 操作键执行“下一个”操作，将用户带到将接受文本的下一个字段。 |
| actionDone        | 动作键执行“完成”操作，关闭软输入法。 |
| actionPrevious    | 操作键执行“上一个”操作，将用户带到将接受文本的上一个字段。 |
| flagNoPersonalizedLearning   | 用于请求 IME 不应根据用户在此文本编辑对象上键入的内容更新任何个性化数据，例如键入历史记录和个性化语言模型。典型的用例是： <ul> <li>当应用程序处于特殊模式时，用户的活动预计不会记录在应用程序的历史记录中。一些网络浏览器和聊天应用程序可能有这种模式。<li> <li>当存储输入历史没有多大意义时。在打字游戏中指定此标志可能有助于避免打字历史被用户在日常生活中不太可能打字的单词填满。另一个例子是，当应用程序已经知道预期的输入不是一个有效的词时（例如，促销代码在任何自然语言中都不是一个有效的词）。<li> <ul> <p>应用程序需要意识到该标志不是保证，某些 IME 可能不尊重它 |
| flagNoFullscreen    | 用于请求 IME 从不进入全屏模式。应用程序需要知道该标志不是保证，并且并非所有 IME 都会尊重它。 |
| flagNavigatePrevious    | 与 flagNavigateNext 类似，但指定了向后导航可以关注的有趣内容。如果用户选择 IME 的工具进行向后导航，这将在应用程序中显示为 {@link android.view.inputmethod.InputConnectionperformEditorAction(int) InputConnection.performEditorAction(int)} 处的 actionPrevious。 |
| flagNavigateNext    | 用于指定前向导航可以关注的一些有趣的事情。这就像使用 actionNext，除了允许 IME 是多行的（带有回车键）以及提供向前导航。请注意，某些 IME 可能无法执行此操作，尤其是在空间很小的小屏幕上运行时。在这种情况下，它不需要为此选项提供 UI。与 actionNext 一样，如果用户选择 IME 的工具进行前向导航，这将显示在应用程序中的 {@link android.view.inputmethod.InputConnectionperformEditorAction(int) InputConnection.performEditorAction(int)}。 |
| flagNoExtractUi    | 用于指定 IME 不需要显示其提取的文本 UI。对于可能是全屏的输入法，通常在横向模式下，这允许它们更小，并让部分应用程序显示在后面。尽管用户对应用程序的访问可能会受到限制，但它可以使（大部分）全屏 IME 的体验不那么刺耳。请注意，当指定此标志时，IME 可能 <em>not<em> 设置为能够显示文本，因此它应该只在不需要的情况下使用。 |
| flagNoAccessoryAction    | 与自定义操作一起使用，这表示当输入法为全屏时，该操作不应作为附件按钮使用。请注意，通过设置此标志，可能会出现用户根本无法使用该操作的情况。设置此项通常意味着您认为显示正在编辑的文本比您提供的操作更重要。 |
| flagNoEnterAction    | 与自定义操作一起使用，这表明该操作不应该作为“回车”键的替代在线使用。通常这是因为该操作具有如此重大的影响或无法恢复，以至于应避免意外击中它，例如发送消息。请注意，{@link android.widget.TextView} 会在多行文本视图中自动为您设置此标志。 |
| flagForceAscii    | 用于请求 IME 应该能够输入 ASCII 字符。此标志的目的是确保用户可以在 {@link android.widget.TextView} 中键入罗马字母字符，通常用于输入帐户 ID 或密码。预计 IME 通常能够在没有被告知的情况下输入 ASCII（这样的 IME 在某种意义上已经尊重此标志），但在某些情况下它们可能不是，例如，只有非 ASCII 输入语言像在 IME 中启用了阿拉伯语、希腊语、希伯来语、俄语。应用程序需要知道该标志不是保证，并且并非所有 IME 都会尊重它。但是，强烈建议 IME 作者尊重此标志，尤其是当他们的 IME 最终可能处于仅启用非 ASCII 输入语言的状态时。 |

### imeActionLabel

为输入法连接到文本视图时使用的 {@link android.view.inputmethod.EditorInfoactionLabel EditorInfo.actionLabel} 提供一个值。

### privateImeOptions

提供给附加到文本视图的输入法的附加内容类型描述，对于输入法的实现是私有的。这只是在输入法连接时填写 {@link android.view.inputmethod.EditorInfoprivateImeOptions EditorInfo.privateImeOptions} 字段

