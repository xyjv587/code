package com.baizhi.serviceimpl;
import com.baizhi.entity.Admin;
import com.baizhi.mapper.AdminMapper;
import com.baizhi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String,Object> login(String username, String password, String code, HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        String code1 = (String) session.getAttribute("code");
        if(code1.equals(code)){
            Admin admin = adminMapper.login(username);
            if(admin!=null){
                if(password.equals(admin.getPassword())){
                    map.put("msg","ok");
                    return map;
                }else {
                    map.put("msg","密码不正确");
                    return map;
                }
            }else {
                map.put("msg","此用户不存在");
                return map;
            }
        }else{
            map.put("msg","验证码错误");
            return map;
        }
    }
}
