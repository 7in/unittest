package com.handsome;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;

/**
 * Created by jiayu.shenjy on 2016/4/11.
 */
public class IMockUtil {
    public static void assembleMockAsk(Class originClazz,Object mockClazz) throws InvocationTargetException, IllegalAccessException {
        DirectForAnswer answer = new DirectForAnswer();
        Method[] originMethods = originClazz.getDeclaredMethods();
        Method[] mockMethods = mockClazz.getClass().getDeclaredMethods();

        List<Method> temps = filter(originMethods,mockMethods);
        for (Method method : temps) {
            Class<?>[] types = method.getParameterTypes();
            when(method.invoke(mockClazz, types)).then(answer);
        }
    }
    /**
     * 被mock的类
     *
     * @param originMethods
     * @param mockMethods
     * @return
     */
    private static List<Method> filter(Method[] originMethods, Method[] mockMethods) {
        List<Method> methodList = new ArrayList<>();

        Map<String, Method> mockMethodMap = new HashMap<String, Method>();
        for (Method mockMethod : mockMethods) {
            String mockMethodKey = mockMethod.getName() + mockMethod.getReturnType() + JSONUtil.toJsonString(mockMethod.getParameterTypes());
            mockMethodMap.put(mockMethodKey, mockMethod);
        }
        for (Method originMethod : originMethods) {
            String methodKey = originMethod.getName() + originMethod.getReturnType() + JSONUtil.toJsonString(originMethod.getParameterTypes());
            if (mockMethodMap.containsKey(methodKey)) {
                methodList.add(mockMethodMap.get(methodKey));
            }
        }

        return methodList;
    }
}
