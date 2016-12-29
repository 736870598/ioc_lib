# ioc_lib
仿xUtils自定义IOC框架，非常好用，高扩展性，大大提升编码效率,xutils中的功能太大了，有时候导入xutils感觉只用了其中一点点东西，弄懂xUtils中的IOC框架原理，干脆自己就写了一套，非常好用，我现在所有的项目基本都在用。

这个ioc中的方法功能分为3大块，分别为1.绑定视图、2.绑定控件、3.绑定事件 其中的原理也很简单，基本在网上都能搜的到。
下面还是简单的介绍一下使用场景和使用原理吧
其中定义的自定义注解有：

     /** 1
     * 此注解用于为Activity的布局，替代setContentView方法
     * 
     * 使用场景：
     * 	@InjectContentView(R.layout.xx)
     *	public class 类名  extends InjectBaseActivity {  
     *		..................
     * 	}
     * 
     * @author 孙晓宇
     */
    @Retention(RetentionPolicy.RUNTIME) //使用场景（运行时）
    @Target(ElementType.TYPE)			//使用地方（type: 类）
    public @interface InjectContentView {
      int value();
    }
    
    
    /** 2
     * 此注解用绑定控件
     * 
     * 使用场景：
     * 	@InjectView(R.id.xx)
     *	private View view;
     * 
     * 	@author 孙晓宇
     */
    @Retention(RetentionPolicy.RUNTIME)  //使用场景（运行时）
    @Target(ElementType.FIELD) 			 //使用地方（FIELD: 属性）
    public @interface InjectView {
      int value();
    }
    
    /** 3
     * 此注解用于监听所有控件点击事件
     * 
     * 使用场景：
     * 	@InjectOnClick({R.id.xx, R.id.xx})
     *	public void 方法名(View view){ 
     *		......................
     * 	}
     * 
     * @author 孙晓宇
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD) //方法
    @InjectEventBus(listenerSetter="setOnClickListener", listenerType=View.OnClickListener.class, callBackMethod="onClick")
    public @interface InjectOnClick {
      int[] value();
    }
    
    
    /** 4
     * 此注解用于监听所有控件长按事件（源码中设定按下没有移除也没有松开500ms后触发长按事件）
     * 
     * 使用场景：
     * 	@InjectOnLongClick({R.id.xx, R.id.xx})
     *	public boolean 方法名(View view){ 
     *		...............
     *		return true;
     * 	}
     * 	注： 长按事件的方法必须返回boolean类型的值
     * 		
     * @author 孙晓宇
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD) //方法
    @InjectEventBus(listenerSetter="setOnLongClickListener", listenerType=View.OnLongClickListener.class, callBackMethod="onLongClick")
    public @interface InjectOnLongClick {
      int[] value();
    }
    
    
    /** 5
     * 此注解用于监听RadioGroup控件的状态改变事件
     * 	
     * 使用场景：
     * 	InjectRBOnCheckedChange({R.id.xx,R.id.xx})
     *	public void 方法名(RadioGroup rg, int radioButtonId){ 
     *		...............................
     *  }
     * 
     * @author 孙晓宇
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD) //方法
    @InjectEventBus(listenerSetter="setOnCheckedChangeListener", listenerType=RadioGroup.OnCheckedChangeListener.class, callBackMethod="onCheckedChanged")
    public @interface InjectRBOnCheckedChange {
      int[] value();
    }
    
    
    /** 6
     * 此注解用于监听CompoundButton及其值控件（CheckBox，ToggleButton等）的状态改变事件
     * 	
     * 使用场景：
     * 	@InjectCBOnCheckedChange({R.id.xx,R.id.xx})
     *	public void 方法名(CompoundButton cButton, boolean isChecked){ 
     *		...............................
     *  }
     * 
     * @author 孙晓宇
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD) 			//方法
    @InjectEventBus(listenerSetter="setOnCheckedChangeListener", listenerType=CompoundButton.OnCheckedChangeListener.class, callBackMethod="onCheckedChanged")
    public @interface InjectCBOnCheckedChange {
      int[] value();
    }
    
    
    /**
     * 此注解为事件添加类型声明，用于注解上
     * @author 孙晓宇
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.ANNOTATION_TYPE) //注解
    public @interface InjectEventBus {

      // 设置事件监听方式      
      String listenerSetter();
      // 事件监听类型
      Class<?> listenerType();
      // 事件触发后回调方法
      String callBackMethod();
    }
    
代码上的注释写的挺清楚了，我觉得已经不用在介绍了，但是光有上面的代码是不行的，下面的才是核心，首先我这里定义了一个InjectBaseActivity,要使用IOC的Activity可以
继承自该InjectBaseActivity，这个InjectBaseActivity只是在onCreate中初始化了我自定义的InjectUtils，当然不继承InjectBaseActivity的话，在代码onCreate中初始化InjectUtils一样是可以的，
下面是我的InjectBaseActivity

    /**
     * 所有使用ioc的activity必须继承该Activity或者在onCreate中实现InjectUtils.inject(this);
     * @author 孙晓宇
     */
    public class InjectBaseActivity extends Activity{
      @Override
      protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectUtils.inject(this);
      }
    }
    
 下面是InjectUtils的构造函数：
 
      /**
       * InjectUtils构造函数
       */
      public static void inject(Activity activity){
        injectLayout(activity);
        injectViews(activity);
        injectEvent(activity);
      }
    
其中injectLayout(activity)是绑定布局功能。代码如下，通过clazz.getAnnotation获取是否有InjectContentView这个自定义注解，有的话取出其中布局文件id，，直接执行setContentView（id）就将布局文件绑定成功了，
可以看出这样写要比先重写onCreate在执行setConentView方法要简单的多。
    
    /**
     * 注入布局
     * 这里传入activity要比传入context处理起来简单些
     * 因为activity有setContentView方法，而context要通过反射获取setContentView方法
     */
    private static void injectLayout(Activity activity){
      Class<? extends Activity> clazz = activity.getClass();
      InjectContentView contentView = clazz.getAnnotation(InjectContentView.class);
      if(contentView == null){
        return ;
      }
      int layoutId = contentView.value();
      //方法一：activity直接调用setContentView
      activity.setContentView(layoutId);
      //方法二：context通过反射获取setContentView方法后进行绑定
      //Method mth = clazz.getMethod("setContentView", int.class);
      //mth.invoke(activity, layoutId);
    }
    
下面是绑定控件功能，同绑定布局基本一样，先获取元素上的注解，如果有自定义注解的话，获取其中控件id，绑定元素，将绑定后的view通过反射的方式赋值给元素即可

    /**
     * 注入控件
     * 同理activity有findViewById方法，而context要通过反射获取findViewById方法
     */
    private static void injectViews(Activity activity) {
      Class<? extends Activity> clazz = activity.getClass();
      Field[] fields = clazz.getDeclaredFields();
      InjectView viewInject;
      View view;
      for (Field field : fields) {
        viewInject = field.getAnnotation(InjectView.class);
        if(viewInject != null){
          //方法一：activity直接调用findViewById
          view = activity.findViewById(viewInject.value());
          //方法二：context通过反射获取findViewById方法后进行赋值
          //method = clazz.getMethod("findViewById", int.class);
          //View view  = (View) method.invoke(context, ViewId);
          try {
            field.setAccessible(true);
            field.set(activity, view);
          } catch (IllegalArgumentException e) {
            e.printStackTrace();
          } catch (IllegalAccessException e) {
            e.printStackTrace();
          }
        }
      }
    }
    
 最后是绑定事件，其实原理和之前的差不多，难点就是在于本来执行事件后要回调接口的回调方法，但是我们想让程序执行我们自定义的方法，而不是接口的回调方法。
 这里就要用到代理模式了，安卓已经将代理模式封装在Proxy中了，而且特别好用，我们可以通过Proxy.newProxyInstance(ClassLoader loader, Class<?>[] interfaces, InvocationHandler h) 
 返回得到代理器，这里要传入的只有InvocationHandler，具体的参数意思在网上都有的。
 
    /**
     * 注入事件
     */
    private static void injectEvent(Activity activity) {
      Class<? extends Activity> clazz = activity.getClass();
      Method[] methods = clazz.getDeclaredMethods();
      for (Method method : methods) {
        Annotation[] annotations = method.getAnnotations();

        for (Annotation annotation : annotations) {
          Class<?> annotationType = annotation.annotationType();
          InjectEventBus eventBus = annotationType.getAnnotation(InjectEventBus.class);
          if(eventBus == null){
            continue;
          }

          //获取三要素，即InjectEventBus中的三个参数，这三个参数为绑定事件的三要素。
          String listenerSetter = eventBus.listenerSetter();
          Class<?> listenerType = eventBus.listenerType();
          String callBackMethod = eventBus.callBackMethod();

          //获取控件
          try {
            Method valueMethod = annotationType.getDeclaredMethod("value");
            int[] viewIds = (int[]) valueMethod.invoke(annotation);
            for (int viewId : viewIds) {
              //这里传入activity和context处理方法不一样，可参考injectViews中
              View view = activity.findViewById(viewId);
              if(view == null){
                continue;
              }

              //获取原本的设置接口方法，点击时原本要响应接口的回调方法，现在通过代理模式让事件响应我们设置的方法。
              Method setListenerMtd = view.getClass().getMethod(listenerSetter, listenerType);
              //eventInvocationHandler中实现代理模式的核心
              EventInvocationHandler eventInvocationHandler = new EventInvocationHandler(activity, callBackMethod, method);
              Object proxy = Proxy.newProxyInstance(listenerType.getClassLoader(), new Class<?>[]{listenerType}, eventInvocationHandler);
              setListenerMtd.invoke(view, proxy);
            }

          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }
    }
    
其中的InvocationHandler就是我们要传入的，这里我们创建了EventInvocationHandler实现InvocationHandler。重写invoke(Object obj, Method method, Object[] args)即可

      /**
       * 代理模式中的InvocationHandler
       * @author 孙晓宇
       */
      public class EventInvocationHandler implements InvocationHandler {

        private Activity activity;
        private String MethodName;
        private Method proxyMethod;

        public EventInvocationHandler(Activity activity, String MethodName, Method proxyMethod) {
          this.activity = activity;
          this.MethodName = MethodName;
          this.proxyMethod = proxyMethod;
        }

        @Override
        public Object invoke(Object obj, Method method, Object[] args) throws Throwable {

          //这里判断如果回调方法是我们被代理的方法，就执行我们的代理方法
          if(method.getName() == MethodName){
            return proxyMethod.invoke(activity, args);
          }
          return method.invoke(obj, args);
        }
      }
      
到这里，基本就已经写完了这套IOC框架，剩下的就是不停的扩展和使用了，我将他打包成了jar包，在需要的项目中直接引入jar包即可。其实如果你真正的理解其中的核心后，你会发现这个框架太好用了，
而且也太好扩展了，另外还要说一下IOC的核心就是反射机制，但是如果大量的使用反射是会影响效率的。
