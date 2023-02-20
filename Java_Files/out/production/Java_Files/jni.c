
#include </usr/lib/jvm/jdk-18/include/jni.h>
#include </usr/lib/jvm/jdk-18/include/linux/jni_md.h>
#include <string.h>
#include <stdio.h>

// Compile Java Class
// system("/usr/lib/jvm/jdk-18/bin/javac /home/amantya/modules/Java_files/ReadWriteFiles.java");
// log_debug("JAVA FILES COMPILATION IS SUCCESSFUL \n");

int main(int argc, char *argv[])
{
	JavaVM *vm;
	JNIEnv *env;
	JavaVMInitArgs vm_args;
	jint res1;
	jclass cls;
	jmethodID mid;
	jstring jstr;
	jobjectArray main_args;
	jobject obj1;
	JavaVMOption options;
	options.optionString = "-Djava.class.path=/home/amantya/JAVA/"; // Path to the java source code
	vm_args.version = JNI_VERSION_1_8;
	vm_args.nOptions = 1;
	vm_args.options = &options;

	res1 = JNI_CreateJavaVM(&vm, (void **)&env, &vm_args);
	if (res1 != JNI_OK)
	{
		printf("Failed to create Java VM \n");
		return 1;
	}

	cls = (*env)->FindClass(env, "ReadWriteFiles");
	if (cls == NULL)
	{
		printf("\n FAILED to FIND ReadWriteFiles class \n");
		return 1;
	}

	//------------------------------Calling a void OBJECT METHOD with INTEGER Arguments -----------------------------------------

	jmethodID constructor = (*env)->GetMethodID(env, cls, "<init>", "()V");
	jobject jobjDet;
	if (constructor != NULL)
	{
		jobjDet = (*env)->NewObject(env, cls, constructor, "");
	}

	//------------------------------Calling a int OBJECT METHOD with STRING Arguments and STRING Return -----------------------------------------

	jmethodID mid6 = (*env)->GetMethodID(env, cls, "readFile", "(Ljava/lang/String;)Ljava/lang/String;");
	if (mid6 == NULL)
	{
		printf("\n readFile METHOD IS NULL \n");
	}
	jstring StringArgName = (*env)->NewStringUTF(env, "/home/amantya/modules/Java_Files/readMe.json");
	jstring sVal = (*env)->CallObjectMethod(env, jobjDet, mid6, StringArgName);

	const char *rStr = (*env)->GetStringUTFChars(env, sVal, 0);

	printf("Return Value from JAVA's returnString function: %s \n", rStr);

	int n = (*vm)->DestroyJavaVM(vm);
	return 0;
}
