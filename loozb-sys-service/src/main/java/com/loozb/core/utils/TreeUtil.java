package com.loozb.core.utils;

import com.loozb.model.ext.SysResourceBean;
import org.apache.commons.collections.CollectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * 将资源转成对应的树关系
 * @Author： 龙召碧
 * @Date: Created in 2017-2-10 0:01
 */
public class TreeUtil {
    @SuppressWarnings({ "unchecked" })
    public static List<SysResourceBean> buildListToTree(List<SysResourceBean> dirs) throws InvocationTargetException, IllegalAccessException {
        List<SysResourceBean> roots = TreeUtil.findRoots(dirs);
        List<SysResourceBean> notRoots = (List<SysResourceBean>) CollectionUtils
                .subtract(dirs, roots);
        for (SysResourceBean root : roots) {
            root.setChildren(TreeUtil.findChildren(root, notRoots));
        }
        return roots;
    }

    private static List<SysResourceBean> findRoots(List<SysResourceBean> allSysResources) throws InvocationTargetException, IllegalAccessException {
        List<SysResourceBean> results = new ArrayList<SysResourceBean>();
        for (SysResourceBean resource : allSysResources) {
            boolean isRoot = true;
            for (SysResourceBean comparedOne : allSysResources) {
                if (resource.getPid() == comparedOne.getId()) {
                    isRoot = false;
                    break;
                }
            }
            if (isRoot) {
                resource.setLeaf(false);
                results.add(resource);
            }
        }
        return results;
    }

    @SuppressWarnings("unchecked")
    private static List<SysResourceBean> findChildren(SysResourceBean root,
                                                      List<SysResourceBean> allSysResources) {
        List<SysResourceBean> children = new ArrayList<SysResourceBean>();

        for (SysResourceBean comparedOne : allSysResources) {
            if (comparedOne.getPid() == root.getId()) {
                children.add(comparedOne);
            }
        }
        List<SysResourceBean> notChildren = (List<SysResourceBean>) CollectionUtils
                .subtract(allSysResources, children);
        for (SysResourceBean child : children) {
            List<SysResourceBean> tmpChildren = findChildren(child, notChildren);
            if (tmpChildren == null || tmpChildren.size() < 1) {
                // 叶子节点，如果needRoot为false，则移除
                child.setLeaf(true);

            } else {
                child.setLeaf(false);
            }
            child.setChildren(tmpChildren);

        }
        return children;
    }
}
