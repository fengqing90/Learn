package com.learnleetcode.栈;

import java.util.Stack;

/**
 * TODO
 *
 * @author fengqing
 * @date 2020/11/25 17:56
 */
public class 栈 {

    // Stack s = new Stack();
    //
    // s.push(1);
    // s.push(2);
    // s.push(3);
    // s.push(4);
    //
    // System.out.println(ArrayUtils.toString(s.toArray()));
    //
    // System.out.println(s.pop());
    // System.out.println(ArrayUtils.toString(s.toArray()));
    //
    // System.out.println(s.peek());
    // System.out.println(ArrayUtils.toString(s.toArray()));

    public static void main(String[] args) {

        最小栈.run();
        最小栈.run2();
    }

    /**
     * 155. 最小栈
     * 
     * <pre>
     * 设计一个支持 push ，pop ，top 操作，并能在常数时间内检索到最小元素的栈。
     *
     * push(x) —— 将元素 x 推入栈中。
     * pop() —— 删除栈顶的元素。
     * top() —— 获取栈顶元素。
     * getMin() —— 检索栈中的最小元素。
     *  
     *
     * 解释：
     * MinStack minStack = new MinStack();
     * minStack.push(-2);
     * minStack.push(0);
     * minStack.push(-3);
     * minStack.getMin();   --> 返回 -3.
     * minStack.pop();
     * minStack.top();      --> 返回 0.
     * minStack.getMin();   --> 返回 -2.
     *  
     *
     * 提示：
     * pop、top 和 getMin 操作总是在 非空栈 上调用。
     * </pre>
     **/
    static class 最小栈 {

        static void run() {
            最小栈.MinStack minStack = new 最小栈.MinStack();
            minStack.push(-2);
            minStack.push(0);
            minStack.push(-3);

            System.out.println(minStack.getMin());   //--> 返回 -3.
            minStack.pop();
            System.out.println(minStack.top());      //--> 返回 0.
            System.out.println(minStack.getMin());   //--> 返回 -2.
        }

        static void run2() {
            最小栈.MinStack minStack = new 最小栈.MinStack();
            minStack.push(-1);
            minStack.push(0);

            System.out.println(minStack.getMin());   //--> 返回 -1.
            minStack.pop();
            System.out.println(minStack.top());      //--> 返回 -1.
            System.out.println(minStack.getMin());   //--> 返回 -1.
        }

        static class MinStack {

            private Stack<Integer> stack;
            private Stack<Integer> minStack;

            /** initialize your data structure here. */
            public MinStack() {
                this.minStack = new Stack<>();
                this.stack = new Stack<>();
            }

            public void push(int x) {
                stack.push(x);

                if (minStack.empty() || x < minStack.peek()) {
                    minStack.push(x);
                } else {
                    minStack.add(minStack.peek());
                }
            }

            public void pop() {
                if (!stack.empty()) {
                    stack.pop();
                    minStack.pop();
                }
            }

            public int top() {
                if (!stack.empty()) {
                    return stack.peek();
                }
                throw new RuntimeException();
            }

            public int getMin() {
                if (!minStack.empty())
                    return minStack.peek();

                throw new RuntimeException();
            }
        }

    }
}
