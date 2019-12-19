안드로이드 커스텀 옵션 메뉴
======================
### 1. 커스텀 옵션 메뉴
안드로이드 라이브러리를 사용하지 않고 옵션 메뉴를 사용하기 위해 개발,
코드 수정으로 툴팁 기능으로도 사용 가능

### 2. 기본 사용법
kotlin code
<pre><code>var customOptionMenuWidget : CustomOptionMenuWidget?= null
..
override fun onCreate(savedInstanceState: Bundle?) {
   super.onCreate(savedInstanceState)
   setContentView(R.layout.activity_main)
..

customOptionMenuWidget = CustomOptionMenuWidget.createCustomOptionMenuWidget(this, R.id.title_right_btn)
        customOptionMenuWidget!!.addItem(OptionMenuType.COPY, View.OnClickListener {
            Toast.makeText(this, "COPY", Toast.LENGTH_SHORT).show()
        })
        customOptionMenuWidget!!.addItem(OptionMenuType.CUT, View.OnClickListener {
            Toast.makeText(this, "CUT", Toast.LENGTH_SHORT).show()
        })
        customOptionMenuWidget!!.addItem(OptionMenuType.PASTE, View.OnClickListener {
            Toast.makeText(this, "PASTE", Toast.LENGTH_SHORT).show()
        })
}
</code></pre>

 
